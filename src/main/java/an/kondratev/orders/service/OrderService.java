package an.kondratev.orders.service;

import an.kondratev.orders.dto.OrderDTO;
import an.kondratev.orders.dto.ProductDTO;
import an.kondratev.orders.mapper.CustomerMapper;
import an.kondratev.orders.mapper.ProductMapper;
import an.kondratev.orders.model.Customer;
import an.kondratev.orders.model.Order;
import an.kondratev.orders.model.Product;
import an.kondratev.orders.repository.CustomerRepository;
import an.kondratev.orders.repository.OrderRepository;
import an.kondratev.orders.repository.ProductRepository;
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
    private final CustomerMapper customerMapper;


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
            customer = customerMapper.toEntity(orderDTO.getCustomerDTO());
            customer = customerRepository.save(customer);
        }

        List<Product> productList = new ArrayList<>();

        for (ProductDTO productDTO : orderDTO.getProductsDTO()) {
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
                .orderStatus(orderDTO.getOrderStatusDTO())
                .shippingAddress(orderDTO.getShippingAddressDTO())
                .paymentStatus(false)
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
        Order existingOrder = orderRepository.findById(orderDTO.getOrderIdDTO())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (orderDTO.getProductsDTO() != null) {
            List<Product> productList = orderDTO.getProductsDTO().stream()
                    .map(productMapper::toEntity)
                    .collect(Collectors.toList());
            existingOrder.setProducts(productList);
        }

        if (orderDTO.getOrderStatusDTO() != null) {
            existingOrder.setOrderStatus(orderDTO.getOrderStatusDTO());
        }

        if (orderDTO.getShippingAddressDTO() != null) {
            existingOrder.setShippingAddress(orderDTO.getShippingAddressDTO());
        }

        if (orderDTO.getTotalPriceDTO() != null) {
            existingOrder.setTotalPrice(orderDTO.getTotalPriceDTO());
        }

        if (orderDTO.isPaymentStatusDTO() != existingOrder.isPaymentStatus()) {
            existingOrder.setPaymentStatus(orderDTO.isPaymentStatusDTO());
        }

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
