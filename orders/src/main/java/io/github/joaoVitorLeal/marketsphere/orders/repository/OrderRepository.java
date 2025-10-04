package io.github.joaoVitorLeal.marketsphere.orders.repository;

import io.github.joaoVitorLeal.marketsphere.orders.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
