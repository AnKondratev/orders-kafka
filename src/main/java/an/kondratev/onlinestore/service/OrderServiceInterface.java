package an.kondratev.onlinestore.service;

import an.kondratev.onlinestore.dto.OrderDTO;
import an.kondratev.onlinestore.model.Order;

import java.util.List;

public interface OrderServiceInterface {
    Order getOrder(Long orderId);
    Order createOrder(OrderDTO orderDTO);
    Order updateOrder(OrderDTO orderDTO);
    void deleteOrder(Long orderId);
    List<Order> getAllOrders();
}
