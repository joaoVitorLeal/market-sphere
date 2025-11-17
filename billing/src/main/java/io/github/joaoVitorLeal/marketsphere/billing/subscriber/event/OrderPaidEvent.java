package io.github.joaoVitorLeal.marketsphere.billing.subscriber.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderPaidEvent(
        Long orderId,
        CustomerPayload customer,
        Instant orderDate,
        String orderObservations,
        BigDecimal orderTotal,
        List<OrderItemPayload> orderItems
) { }
