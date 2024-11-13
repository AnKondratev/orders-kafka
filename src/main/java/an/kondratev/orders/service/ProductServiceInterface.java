package an.kondratev.orders.service;

import an.kondratev.orders.dto.ProductDTO;
import an.kondratev.orders.model.Product;

import java.util.List;

public interface ProductServiceInterface {
    Product getProduct(Long id);

    Product createProduct(ProductDTO productDTO);

    Product updateProduct(ProductDTO productDTO);

    void deleteProduct(Long id);

    List<Product> getAllProducts();
}
