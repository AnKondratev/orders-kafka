package an.kondratev.orders.kafka;

import an.kondratev.orders.dto.OrderDTO;
import an.kondratev.orders.model.Order;
import an.kondratev.orders.service.OrderServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
class KafkaConsumerTest {

    @InjectMocks
    private KafkaConsumer kafkaConsumer;

    @Mock
    private OrderServiceInterface orderService;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenInvalidJsonReceived_thenRuntimeExceptionIsThrown() throws JsonProcessingException {
        String invalidJsonOrder = "invalid json";

        when(objectMapper.readValue(invalidJsonOrder, Order.class)).thenThrow(new JsonProcessingException("Parsing error") {
        });

        assertThrows(RuntimeException.class, () -> kafkaConsumer.listenOrder(invalidJsonOrder));

        verify(orderService, never()).updateOrder(any(OrderDTO.class));
    }
}

