package io.github.joaoVitorLeal.marketsphere.shipping.publisher.event;

import java.time.Instant;
import java.util.UUID;

public record OrderShippedEvent(
        Long orderId,
        UUID trackingCode,
        Instant shippedAt
) { }
