package an.kondratev.onlinestore.service;

import an.kondratev.orders.dto.ProductDTO;
import an.kondratev.orders.mapper.ProductMapper;
import an.kondratev.orders.model.Product;
import an.kondratev.orders.repository.ProductRepository;
import an.kondratev.orders.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct_SavesProductSuccessfully() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setNameDTO("Test Product");
        productDTO.setDescriptionDTO("Test Description");
        productDTO.setPriceDTO(new BigDecimal("10.00"));
        productDTO.setQuantityInStockDTO(100L);

        Product savedProduct = new Product();
        savedProduct.setProductId(1L);

        // Stub mapper and repository behaviors
        when(productMapper.toEntity(productDTO)).thenReturn(savedProduct);
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // Call the service method
        Product result = productService.createProduct(productDTO);

        // Validate the result
        assertNotNull(result);
        assertEquals(1L, result.getProductId());
    }

    @Test
    void getProduct_ReturnsProduct() {
        // Arrange
        Product product = new Product();
        product.setProductId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        Product result = productService.getProduct(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getProductId());
    }
}

