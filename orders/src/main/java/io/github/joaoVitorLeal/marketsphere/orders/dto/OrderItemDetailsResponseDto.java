package io.github.joaoVitorLeal.marketsphere.orders.dto;

import java.math.BigDecimal;

public record OrderItemDetailsResponseDto(
        Long productId,
        String productName,
        Integer amount,
        BigDecimal unitPrice
) {

    private BigDecimal getSubTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(amount));
    }
}
