package an.kondratev.onlinestore.service;

import an.kondratev.onlinestore.dto.OrderDTO;
import an.kondratev.onlinestore.model.Customer;
import an.kondratev.onlinestore.model.Order;
import an.kondratev.onlinestore.model.Product;
import an.kondratev.onlinestore.repository.CustomerRepository;
import an.kondratev.onlinestore.repository.OrderRepository;
import an.kondratev.onlinestore.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    private OrderService orderService;
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        customerRepository = mock(CustomerRepository.class);
        productRepository = mock(ProductRepository.class);
        orderService = new OrderService(orderRepository, customerRepository, productRepository, null);
    }

    @Test
    void createOrder_CreatesOrderSuccessfully() {
        Customer customer = new Customer();
        customer.setCustomerId(1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(productRepository.findById(1L)).thenReturn(Optional.of(new Product()));

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCustomerId(1L);
        orderDTO.setProducts(List.of());
        orderDTO.setShippingAddress("123 Street");
        orderDTO.setOrderStatus("CREATED");
        orderDTO.setTotalPrice(new BigDecimal("100.00"));

        Order savedOrder = new Order();
        savedOrder.setOrderId(1L);
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        Order result = orderService.createOrder(orderDTO);

        assertNotNull(result);
        assertEquals(1L, result.getOrderId());
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void getOrder_ReturnsOrder() {
        Order order = new Order();
        order.setOrderId(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order result = orderService.getOrder(1L);

        assertNotNull(result);
        assertEquals(1L, result.getOrderId());
    }
}

