package an.kondratev.onlinestore.controller;

import an.kondratev.onlinestore.dto.OrderDTO;
import an.kondratev.onlinestore.dto.ProductDTO;
import an.kondratev.onlinestore.model.Order;
import an.kondratev.onlinestore.model.Product;
import an.kondratev.onlinestore.service.OrderServiceInterface;
import an.kondratev.onlinestore.service.ProductServiceInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
public class StoreController {
    private final ProductServiceInterface productService;
    private final OrderServiceInterface orderService;
    private final ObjectMapper objectMapper;

    @GetMapping("all_products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);

    }

    @PostMapping("create_product")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.createProduct(productDTO), HttpStatus.CREATED);
    }

    @PutMapping("update_product")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.updateProduct(productDTO), HttpStatus.OK);
    }

    @DeleteMapping("delete_product/{id}")
    public HttpStatus deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return HttpStatus.OK;
    }

    @PostMapping("create_order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO) {
        return new ResponseEntity<>(orderService.createOrder(orderDTO), HttpStatus.CREATED);
    }

    @GetMapping("order/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(orderService.getOrder(id), HttpStatus.OK);
    }
}

