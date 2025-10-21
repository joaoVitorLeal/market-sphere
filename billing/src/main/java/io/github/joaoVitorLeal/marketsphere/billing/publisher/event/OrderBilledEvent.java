package io.github.joaoVitorLeal.marketsphere.billing.publisher.event;

import io.github.joaoVitorLeal.marketsphere.billing.publisher.event.enums.OrderStatus;

public record OrderBilledEvent(
        Long orderId,
        OrderStatus newOrderStatus,
        String invoiceUrl
) { }
