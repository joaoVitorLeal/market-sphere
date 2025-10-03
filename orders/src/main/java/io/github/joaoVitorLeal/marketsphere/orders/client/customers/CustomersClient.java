package io.github.joaoVitorLeal.marketsphere.orders.client.customers;

import io.github.joaoVitorLeal.marketsphere.orders.client.customers.representation.CustomerRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "customers",
        url = "${market-sphere.feign.clients.customers.base-url}"
)
public interface CustomersClient {

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerRepresentation> getCustomerById(@PathVariable Long customerId);
}
