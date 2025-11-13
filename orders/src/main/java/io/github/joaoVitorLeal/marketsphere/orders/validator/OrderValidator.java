package io.github.joaoVitorLeal.marketsphere.orders.validator;

import feign.FeignException;
import io.github.joaoVitorLeal.marketsphere.orders.client.customers.CustomersClient;
import io.github.joaoVitorLeal.marketsphere.orders.client.customers.representation.CustomerRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.client.products.ProductsClient;
import io.github.joaoVitorLeal.marketsphere.orders.client.products.representation.ProductRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderItemRequestDto;
import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderRequestDto;
import io.github.joaoVitorLeal.marketsphere.orders.exception.client.customers.CustomerClientNotFoundException;
import io.github.joaoVitorLeal.marketsphere.orders.exception.client.products.ProductClientNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderValidator {

    private final ProductsClient productsClient;
    private final CustomersClient customersClient;

    public void validate(OrderRequestDto orderRequestDto) {
        validateCustomer(orderRequestDto.customerId());
        orderRequestDto.orderItems()
                .stream()
                .map(OrderItemRequestDto::productId)
                .forEach(this::validateProduct);
    }

    private void validateCustomer(Long customerId) {
        try {
            ResponseEntity<CustomerRepresentation> response = customersClient.getCustomerById(customerId);
            CustomerRepresentation customerRepresentation = response.getBody();

            if (customerRepresentation == null) {
                log.error("Customer service returned a null body (200 OK) for customer ID: {}.", customerId);
                throw new CustomerClientNotFoundException("customerId", "Customer not found or returned an empty response for ID: " + customerId);
            }

            log.info("Customer found with ID '{}' and name '{}'.", customerId, customerRepresentation.fullName());

        } catch (FeignException.NotFound e) {
            log.error("Customer not found (404) via Feign client for customerId: {}. Message: {}", customerId, e.getMessage());
            throw new CustomerClientNotFoundException("customerId", "Customer not found with ID: " + customerId);
        }
    }

    private void validateProduct(Long productId) {
        try {
            ResponseEntity<ProductRepresentation> response = productsClient.getProductById(productId);
            ProductRepresentation productRepresentation = response.getBody();

            if (productRepresentation == null) {
                log.error("Product service returned a null body (200 OK) for productId: {}.", productId);
                throw new ProductClientNotFoundException("productId", "Product not found or returned an empty response for ID: " + productId);
            }

            log.info("Product found with ID '{}' and name '{}'.", productRepresentation.id(), productRepresentation.name());

        } catch (FeignException.NotFound e) {
            log.error("Product not found (404) via Feign client for productId: {}. Message: {}", productId, e.getMessage());
            throw new ProductClientNotFoundException("productId", "Product not found with ID: " + productId);
        }
    }
}
















