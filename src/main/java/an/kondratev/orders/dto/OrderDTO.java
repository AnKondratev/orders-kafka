package an.kondratev.orders.dto;

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
    private Long orderIdDTO;
    private String shippingAddressDTO;
    private BigDecimal totalPriceDTO;
    private String orderStatusDTO;
    private List<ProductDTO> productsDTO;
    private CustomerDTO customerDTO;
    private boolean paymentStatusDTO;
    private Long customerId;
}