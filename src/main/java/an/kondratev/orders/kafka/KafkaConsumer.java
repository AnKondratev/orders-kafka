package an.kondratev.orders.kafka;

import an.kondratev.orders.dto.OrderDTO;
import an.kondratev.orders.model.Order;
import an.kondratev.orders.service.OrderServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final OrderServiceInterface orderService;

    @KafkaListener(topics = "update_status", groupId = "my_consumer")
    public void listenOrder(String order) {
        try {
            Order newOrder = objectMapper.readValue(order, Order.class);
            OrderDTO orderDTO = OrderDTO.builder()
                    .orderIdDTO(newOrder.getOrderId())
                    .orderStatusDTO(newOrder.getOrderStatus())
                    .paymentStatusDTO(newOrder.isPaymentStatus())
                    .build();
            orderService.updateOrder(orderDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Received Message: Order completed!");
    }
}
