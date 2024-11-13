package an.kondratev.orders.controller;

import an.kondratev.orders.dto.OrderSendDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import an.kondratev.orders.model.Order;
import an.kondratev.orders.service.OrderServiceInterface;
import an.kondratev.orders.kafka.KafkaProducer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

public class MessagesControllerTest {

    @InjectMocks
    private MessagesController messagesController;

    @Mock
    private KafkaProducer producer;

    @Mock
    private OrderServiceInterface orderService;

    @Mock
    private ObjectMapper objectMapper;

    private Order order;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        order = Order.builder()
                .orderId(1L)
                .totalPrice(BigDecimal.valueOf(100))
                .orderStatus("SENT")
                .paymentStatus(true)
                .customer(new Customer("John", "Doe")) // Assume Customer class is defined
                .build();
    }

    @Test
    public void testSendOrder_Success() throws Exception {
        when(orderService.getOrder(1L)).thenReturn(order);
        when(objectMapper.writeValueAsString(any(OrderSendDTO.class))).thenReturn("{jsonOrder}");

        String response = messagesController.sendOrder(1L);

        assertEquals("Order 1 sent successfully", response);
        verify(producer).sandMessageWithRetry("{jsonOrder}", 5);
    }

    @Test
    public void testSendOrder_OrderNotFound() {
        when(orderService.getOrder(1L)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> messagesController.sendOrder(1L));

        assertEquals("Order #1 not found", exception.getMessage());
    }

    @Test
    public void testSendOrder_JsonProcessingException() throws Exception {
        when(orderService.getOrder(1L)).thenReturn(order);
        when(objectMapper.writeValueAsString(any(OrderSendDTO.class))).thenThrow(new JsonProcessingException("Error") {
        });

        String response = messagesController.sendOrder(1L);

        assertEquals("Failed to convert order to JSON", response);
        verify(producer, never()).sandMessageWithRetry(anyString(), anyInt());
    }

    @Test
    public void testSendOrder_ExecutionException() throws Exception {
        when(orderService.getOrder(1L)).thenReturn(order);
        when(objectMapper.writeValueAsString(any(OrderSendDTO.class))).thenReturn("{jsonOrder}");
        doThrow(new ExecutionException("Kafka error", new Throwable()))
                .when(producer).sandMessageWithRetry(anyString(), anyInt());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> messagesController.sendOrder(1L));

        assertNotNull(exception);
        assertEquals("java.util.concurrent.ExecutionException: Kafka error", exception.getMessage());
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    private static class Customer extends an.kondratev.orders.model.Customer {
        private final String firstName;
        private final String lastName;
    }
}