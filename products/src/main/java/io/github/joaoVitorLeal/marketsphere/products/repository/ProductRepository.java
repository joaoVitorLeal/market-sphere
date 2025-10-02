package io.github.joaoVitorLeal.marketsphere.products.repository;

import io.github.joaoVitorLeal.marketsphere.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
