package io.github.joaoVitorLeal.marketsphere.orders.repository;

import io.github.joaoVitorLeal.marketsphere.orders.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
