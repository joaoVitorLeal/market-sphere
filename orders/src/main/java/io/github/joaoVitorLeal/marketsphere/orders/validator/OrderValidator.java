package io.github.joaoVitorLeal.marketsphere.orders.validator;

import io.github.joaoVitorLeal.marketsphere.orders.client.customers.representation.CustomerRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.client.products.representation.ProductRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderItemRequestDto;
import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderRequestDto;
import io.github.joaoVitorLeal.marketsphere.orders.facade.OrderDependenciesFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderValidator {

    private final OrderDependenciesFacade orderDependenciesFacade;

    public void validate(OrderRequestDto orderRequestDto) {
        validateCustomer(orderRequestDto.customerId());
        orderRequestDto.orderItems()
                .stream()
                .map(OrderItemRequestDto::productId)
                .forEach(this::validateProduct);
    }

    private void validateCustomer(Long customerId) {
        CustomerRepresentation customerRepresentation = orderDependenciesFacade.getCustomerRepresentationById(customerId);
        log.info("Customer found with ID '{}' and name {}.", customerRepresentation.id(), customerRepresentation.fullName());

    }

    private void validateProduct(Long productId) {
        ProductRepresentation productRepresentation = orderDependenciesFacade.getProductRepresentationById(productId);
        log.info("Product found with ID '{}' and name '{}'.", productRepresentation.id(), productRepresentation.name());
    }
}
