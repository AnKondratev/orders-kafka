package an.kondratev.orders.kafka;

import an.kondratev.orders.dto.OrderSendDTO;
import an.kondratev.orders.model.Order;
import an.kondratev.orders.service.OrderServiceInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class KafkaProducer {

    private final ObjectMapper objectMapper;
    private final OrderServiceInterface orderService;
    KafkaTemplate<String, String> kafkaTemplate;

    @SneakyThrows
    public String sendOrderById(Long id) {
        Order order = orderService.getOrder(id);
        if (order == null) {
            log.error("Order #{} not found", id);
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

        String jsonOrder = objectMapper.writeValueAsString(orderSendDTO);

        try {
            kafkaTemplate.send("new_orders", customerFullName, jsonOrder);
            log.info("Order #{} sent to kafka topic", id);
        } catch (Exception e) {
            log.error("Error while sending order: {}", e.getMessage());
            throw e;
        }
        return "Order #" + id + " sent successfully";
    }
}