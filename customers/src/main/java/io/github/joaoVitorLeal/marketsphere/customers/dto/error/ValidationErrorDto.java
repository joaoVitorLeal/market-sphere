package io.github.joaoVitorLeal.marketsphere.customers.dto.error;

public record ValidationErrorDto(
        String field,
        String error
) { }
