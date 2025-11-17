package io.github.joaoVitorLeal.marketsphere.billing.publisher.event;

import java.time.Instant;

public record OrderBilledEvent(
        Long orderId,
        String invoiceUrl,
        Instant billedAt
) { }
