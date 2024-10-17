package an.kondratev.onlinestore.service;

import an.kondratev.onlinestore.dto.ProductDTO;
import an.kondratev.onlinestore.mapper.ProductMapper;
import an.kondratev.onlinestore.model.Product;
import an.kondratev.onlinestore.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService implements ProductServiceInterface {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product getProduct(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Product createProduct(ProductDTO productDTO) {
        return productRepository.save(productMapper.toEntity(productDTO));
    }

    @Override
    public Product updateProduct(ProductDTO productDTO) {
        return productRepository.save(productMapper.toEntity(productDTO));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
