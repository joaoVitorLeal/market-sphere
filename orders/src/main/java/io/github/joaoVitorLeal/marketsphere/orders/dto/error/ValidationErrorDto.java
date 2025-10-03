package io.github.joaoVitorLeal.marketsphere.orders.dto.error;

public record ValidationErrorDto(
        String field,
        String error
) { }
