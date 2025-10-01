package io.github.joaoVitorLeal.marketsphere.customers.repository;

import io.github.joaoVitorLeal.marketsphere.customers.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
