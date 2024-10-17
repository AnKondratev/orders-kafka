package an.kondratev.onlinestore.mapper;

import an.kondratev.onlinestore.dto.ProductDTO;
import an.kondratev.onlinestore.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }

        return Product.builder()
                .productId(productDTO.getProductIdDTO())
                .name(productDTO.getNameDTO())
                .description(productDTO.getDescriptionDTO())
                .price(productDTO.getPriceDTO())
                .quantityInStock(productDTO.getQuantityInStockDTO())
                .build();
    }

}