package io.github.joaoVitorLeal.marketsphere.orders.service;

import io.github.joaoVitorLeal.marketsphere.orders.model.Order;
import io.github.joaoVitorLeal.marketsphere.orders.subscriber.event.OrderBilledEvent;

public interface OrderLifecycleService {

    void processSuccessfulPayment(Long orderId);
    void processPaymentError(Order order, String observations);
    void processOrderBilled(OrderBilledEvent orderBilledEvent);
}
