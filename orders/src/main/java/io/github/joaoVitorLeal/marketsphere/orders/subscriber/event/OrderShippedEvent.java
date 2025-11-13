package io.github.joaoVitorLeal.marketsphere.orders.subscriber.event;

import java.time.Instant;

public record OrderShippedEvent(
        Long orderId,
        String trackingCode,
        Instant shippedAt
) { }
