package io.github.joaoVitorLeal.marketsphere.orders.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "order_date", nullable = false, updatable = false)
    private Instant orderDate;

    @Column(name = "payment_key", columnDefinition = "TEXT")
    private String paymentKey;

    @Column(columnDefinition = "TEXT")
    private String observations;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OrderStatus status;

    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal total;

    @Column(name = "tracking_code")
    private UUID trackingCode;

    @Column(name = "invoice_url", columnDefinition = "TEXT")
    private String invoiceUrl;
}
