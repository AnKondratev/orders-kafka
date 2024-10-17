package an.kondratev.onlinestore.service;

import an.kondratev.onlinestore.dto.ProductDTO;
import an.kondratev.onlinestore.model.Product;

import java.util.List;

public interface ProductServiceInterface {
    Product getProduct(Long id);

    Product getProduct(String name);

    Product createProduct(ProductDTO productDTO);

    Product updateProduct(ProductDTO productDTO);

    void deleteProduct(Long id);

    List<Product> getAllProducts();
}
