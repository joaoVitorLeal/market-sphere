package io.github.joaoVitorLeal.marketsphere.orders.publisher.representation;

import java.math.BigDecimal;

public record OrderItemRepresentation (
        Long productId,
        String productName,
        Integer amount,
        BigDecimal unitPrice
) {

    private BigDecimal getSubTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(amount));
    }
}
