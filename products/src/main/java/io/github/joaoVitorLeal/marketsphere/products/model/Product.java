package io.github.joaoVitorLeal.marketsphere.products.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "unit_price", nullable = false, precision = 16, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    // Adapter to dto of request
    public Product(String name, BigDecimal unitPrice, String description) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.description = description;
    }
}
