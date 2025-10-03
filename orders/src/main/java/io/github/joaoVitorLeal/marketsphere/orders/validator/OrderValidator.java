package io.github.joaoVitorLeal.marketsphere.orders.validator;

import feign.FeignException;
import io.github.joaoVitorLeal.marketsphere.orders.client.customers.CustomersClient;
import io.github.joaoVitorLeal.marketsphere.orders.client.customers.representation.CustomerRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.client.products.ProductsClient;
import io.github.joaoVitorLeal.marketsphere.orders.client.products.representation.ProductRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderItemDto;
import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderRequestDto;
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
        Long customerId = orderRequestDto.customerId();
        validateCustomer(customerId);
        orderRequestDto.orderItems().forEach(this::validateOrderItem);
    }

    private void validateCustomer(Long customerId) {
        try {
            ResponseEntity<CustomerRepresentation> response = customersClient.getCustomerById(customerId);
            CustomerRepresentation customerRepresentation = response.getBody();

            assert customerRepresentation != null;
            log.info("Customer found with ID '{}' and name '{}'.", customerId, customerRepresentation.fullName());
        } catch (FeignException.NotFound e) {
            log.error("Customer not found with ID: {}.", customerId);
        }


    }

    private void validateOrderItem(OrderItemDto orderItemDto) {
        try {
            ResponseEntity<ProductRepresentation> response = productsClient.getProductById(orderItemDto.productId());
            ProductRepresentation productRepresentation = response.getBody();

            assert productRepresentation != null;
            log.info("Product found with ID '{}' and name '{}'.", productRepresentation.id(), productRepresentation.name());

        } catch (FeignException.NotFound e) {
            log.error("Product not found with ID: {}.", orderItemDto.productId());
        }
    }
}
















