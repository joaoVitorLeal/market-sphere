package io.github.joaoVitorLeal.marketsphere.orders.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderItemRequestDto(

        @NotNull(message = "{order.orderItems.productId.required}")
        @Positive(message = "{order.OrderItems.productId.positive}")
        Long productId,

        @NotNull(message = "{order.orderItems.amount.required}")
        @Positive(message = "{order.OrderItems.amount.positive}")
        Integer amount,

        @NotNull(message = "{order.orderItems.unitPrice.required}")
        @Positive(message = "{order.OrderItems.unitPrice.positive}")
        BigDecimal unitPrice
) { }
