package an.kondratev.onlinestore.service;

import an.kondratev.onlinestore.dto.OrderDTO;
import an.kondratev.onlinestore.dto.ProductDTO;
import an.kondratev.onlinestore.mapper.ProductMapper;
import an.kondratev.onlinestore.model.Customer;
import an.kondratev.onlinestore.model.Order;
import an.kondratev.onlinestore.model.Product;
import an.kondratev.onlinestore.repository.CustomerRepository;
import an.kondratev.onlinestore.repository.OrderRepository;
import an.kondratev.onlinestore.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService implements OrderServiceInterface {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }


    @Override
    public Order createOrder(OrderDTO orderDTO) {
        Customer customer;
        if (orderDTO.getCustomerId() != null) {
            customer = customerRepository.findById(orderDTO.getCustomerId()).orElse(null);
        } else {
            customer = Customer.builder()
                    .firstName(orderDTO.getCustomer().getFirstName())
                    .lastName(orderDTO.getCustomer().getLastName())
                    .email(orderDTO.getCustomer().getEmail())
                    .phone(orderDTO.getCustomer().getPhone())
                    .build();
            customer = customerRepository.save(customer);
        }

        List<Product> productList = new ArrayList<>();

        for (ProductDTO productDTO : orderDTO.getProducts()) {
            Optional<Product> productOpt = productRepository.findById(productDTO.getProductIdDTO());

            if (productOpt.isPresent()) {
                Product product = productOpt.get();
                if (product.getQuantityInStock() > 0) {
                    productList.add(product);
                }
            }
        }

        Order order = Order.builder()
                .customer(customer)
                .products(productList)
                .totalPrice(calculateTotalPrice(productList))
                .orderStatus(orderDTO.getOrderStatus())
                .shippingAddress(orderDTO.getShippingAddress())
                .build();
        return orderRepository.save(order);
    }

    private BigDecimal calculateTotalPrice(List<Product> products) {
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Order updateOrder(OrderDTO orderDTO) {

        Order existingOrder = orderRepository.findById(orderDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (orderDTO.getProducts() != null) {
            List<Product> productList = orderDTO.getProducts().stream()
                    .map(productMapper::toEntity)
                    .collect(Collectors.toList());
            existingOrder.setProducts(productList);
        }

        existingOrder.setOrderStatus(orderDTO.getOrderStatus());
        existingOrder.setTotalPrice(orderDTO.getTotalPrice());
        existingOrder.setShippingAddress(orderDTO.getShippingAddress());

        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
