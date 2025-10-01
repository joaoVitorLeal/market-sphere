package io.github.joaoVitorLeal.marketsphere.products.dto.error;

public record ValidationErrorDto(
        String field,
        String error
) { }
