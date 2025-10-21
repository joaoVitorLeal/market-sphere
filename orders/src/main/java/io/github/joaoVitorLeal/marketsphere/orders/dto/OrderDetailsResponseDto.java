package io.github.joaoVitorLeal.marketsphere.orders.dto;

import io.github.joaoVitorLeal.marketsphere.orders.client.customers.representation.CustomerRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderDetailsResponseDto(
        Long orderId,
        CustomerRepresentation customer,
        Instant orderDate,
        BigDecimal orderTotal,
        OrderStatus orderStatus,
        String orderObservations,
        List<OrderItemDetailsResponseDto> orderItems
) { }
