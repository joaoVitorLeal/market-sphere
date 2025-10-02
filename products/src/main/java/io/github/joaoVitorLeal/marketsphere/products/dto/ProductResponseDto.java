package io.github.joaoVitorLeal.marketsphere.products.dto;

import java.math.BigDecimal;

public record ProductResponseDto(
        Long id,
        String name,
        BigDecimal unitPrice,
        String description
) { }
