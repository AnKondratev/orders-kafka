package an.kondratev.onlinestore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private String shippingAddress;
    private BigDecimal totalPrice;
    private String orderStatus;
    private List<ProductDTO> products;
    private CustomerDTO customer;
    private Long customerId;
}