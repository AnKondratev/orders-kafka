package an.kondratev.orders.kafka;

import an.kondratev.orders.dto.OrderDTO;
import an.kondratev.orders.model.Order;
import an.kondratev.orders.service.OrderServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final OrderServiceInterface orderService;

    @Transactional
    @KafkaListener(topics = "update_status", groupId = "my_consumer", concurrency = "5")
    public void listenOrder(String order) {
        try {
            Order newOrder = objectMapper.readValue(order, Order.class);
            OrderDTO orderDTO = OrderDTO.builder()
                    .orderIdDTO(newOrder.getOrderId())
                    .orderStatusDTO(newOrder.getOrderStatus())
                    .paymentStatusDTO(newOrder.isPaymentStatus())
                    .build();
            orderService.updateOrder(orderDTO);
            log.info("Order {} updated successfully", newOrder.getOrderId());
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        log.info("Order completed!");
        System.out.println("Received Message: Order completed!");
    }
}
