package an.kondratev.orders.service;

import an.kondratev.orders.dto.OrderDTO;
import an.kondratev.orders.model.Order;

import java.util.List;

public interface OrderServiceInterface {
    Order getOrder(Long orderId);
    Order createOrder(OrderDTO orderDTO);
    Order updateOrder(OrderDTO orderDTO);
    void deleteOrder(Long orderId);
    List<Order> getAllOrders();
}
