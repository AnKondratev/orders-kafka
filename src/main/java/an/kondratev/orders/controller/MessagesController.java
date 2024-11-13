package an.kondratev.orders.controller;

import an.kondratev.orders.dto.OrderSendDTO;
import an.kondratev.orders.kafka.KafkaProducer;
import an.kondratev.orders.model.Order;
import an.kondratev.orders.service.OrderServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@AllArgsConstructor
public class MessagesController {

    private final KafkaProducer producer;
    private final OrderServiceInterface orderService;
    private final ObjectMapper objectMapper;

    @PostMapping("kafka/send_order/{id}")
    public String sendOrder(@PathVariable("id") Long id) {
        Order order = orderService.getOrder(id);
        if (order == null) {
            log.error("Order #{}not found", id);
            throw new RuntimeException("Order #" + id + " not found");
        }
        String customerFullName = order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName();
        OrderSendDTO orderSendDTO = OrderSendDTO.builder()
                .orderId(id)
                .customer(customerFullName)
                .totalPrice(order.getTotalPrice())
                .orderStatus(order.getOrderStatus())
                .paymentStatus(order.isPaymentStatus())
                .build();
        try {
            String jsonOrder = objectMapper.writeValueAsString(orderSendDTO);
            producer.sandMessageWithRetry(jsonOrder, 5);
            log.info("Order #{} sent successfully", id);
            return "Order " + id + " sent successfully";
        } catch (JsonProcessingException e) {
            log.error("Failed to convert order to JSON: {}", e.getMessage());
            return "Failed to convert order to JSON";
        } catch (ExecutionException | InterruptedException e) {
            log.error("ERROR: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}