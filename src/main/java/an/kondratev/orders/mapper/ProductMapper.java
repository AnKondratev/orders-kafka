package an.kondratev.orders.mapper;

import an.kondratev.orders.dto.ProductDTO;
import an.kondratev.orders.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }

        return Product.builder()

                .name(productDTO.getNameDTO())
                .description(productDTO.getDescriptionDTO())
                .price(productDTO.getPriceDTO())
                .quantityInStock(productDTO.getQuantityInStockDTO())
                .build();
    }

}