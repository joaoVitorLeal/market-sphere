package io.github.joaoVitorLeal.marketsphere.orders.facade.client;

import feign.FeignException;
import io.github.joaoVitorLeal.marketsphere.orders.client.customers.CustomersClient;
import io.github.joaoVitorLeal.marketsphere.orders.client.customers.representation.CustomerRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.exception.client.customers.CustomerClientNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerClientServiceImpl implements CustomerClientService {

    private final CustomersClient customersClient;

    @Override
    public CustomerRepresentation getCustomerById(Long customerId) {
        try {
            ResponseEntity<CustomerRepresentation> response = customersClient.getCustomerById(customerId);

            return Optional.ofNullable(response.getBody())
                    .orElseThrow(() -> {
                        log.error("Customer service returned a null body (200 OK) for customerId: {}.", customerId);
                        return new CustomerClientNotFoundException("customerId", "Customer not found or returned an empty response for ID: " + customerId);
                    });
        } catch (FeignException.NotFound e) {
            log.error("Customer not found (404) via Feign client for customer ID: {}. Message: {}", customerId, e.getMessage());
            throw new CustomerClientNotFoundException("customerId", "Customer not found with ID: " + customerId);
        }
    }
}
