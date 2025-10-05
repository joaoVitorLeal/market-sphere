package io.github.joaoVitorLeal.marketsphere.orders.repository;

import io.github.joaoVitorLeal.marketsphere.orders.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByIdAndPaymentKey(Long id, String paymentKey);
}
