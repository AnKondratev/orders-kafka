package an.kondratev.orders.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Column(nullable = false, length = 300)
    private String shippingAddress;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false, length = 100)
    private String orderStatus;

    @Column(nullable = false)
    private boolean paymentStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Product> products;

    @PrePersist
    private void prePersist() {
        this.orderDate = LocalDateTime.now();
    }
}