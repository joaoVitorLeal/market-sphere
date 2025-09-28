package io.github.joaoVitorLeal.marketsphere.products.controller.global;

public record ValidationErrorDto(
        String field,
        String error
) { }
