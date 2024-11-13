package an.kondratev.orders.service;

import an.kondratev.orders.dto.ProductDTO;
import an.kondratev.orders.mapper.ProductMapper;
import an.kondratev.orders.model.Product;
import an.kondratev.orders.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ProductService implements ProductServiceInterface {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Product getProduct(Long id) {
        log.info("Get product by id: {}", id);
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(ProductDTO productDTO) {
        log.info("Create product: {}", productDTO);
        return productRepository.save(productMapper.toEntity(productDTO));
    }

    @Override
    public Product updateProduct(ProductDTO productDTO) {
        log.info("Update product: {}", productDTO);
        return productRepository.save(productMapper.toEntity(productDTO));
    }

    @Override
    public void deleteProduct(Long id) {
        log.info("Delete product: {}", id);
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
