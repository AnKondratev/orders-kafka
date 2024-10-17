package an.kondratev.onlinestore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 200)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Long quantityInStock;

    @ManyToOne()
    @JsonIgnore
    private Order order;
}
//productId (идентификатор продукта)
// name (название продукта)
// description (описание продукта)
// price (цена продукта)
// quantityInStock (количество на складе)

