package io.github.joaoVitorLeal.marketsphere.orders.client.products.representation;

import java.math.BigDecimal;

public record ProductRepresentation(
    Long id,
    String name,
    BigDecimal unitPrice,
    String description
) { }
