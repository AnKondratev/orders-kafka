package an.kondratev.onlinestore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long productIdDTO;
    private String nameDTO;
    private String descriptionDTO;
    private BigDecimal priceDTO;
    private Long quantityInStockDTO;
}