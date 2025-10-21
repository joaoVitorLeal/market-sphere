package io.github.joaoVitorLeal.marketsphere.billing.common.dto.error;

public record ValidationErrorDto(
        String field,
        String error
) { }
