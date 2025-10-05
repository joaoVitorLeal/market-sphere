package io.github.joaoVitorLeal.marketsphere.orders.service.webhook.impl;

import io.github.joaoVitorLeal.marketsphere.orders.dto.webhook.PaymentNotificationDto;
import io.github.joaoVitorLeal.marketsphere.orders.exception.OrderNotFoundException;
import io.github.joaoVitorLeal.marketsphere.orders.model.Order;
import io.github.joaoVitorLeal.marketsphere.orders.model.enums.OrderStatus;
import io.github.joaoVitorLeal.marketsphere.orders.repository.OrderRepository;
import io.github.joaoVitorLeal.marketsphere.orders.service.webhook.PaymentWebhookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentWebhookServiceImpl implements PaymentWebhookService {

    private final OrderRepository orderRepository;

    @Override
    public void updatePaymentStatus(PaymentNotificationDto paymentNotificationDto) {
        Long orderId = paymentNotificationDto.orderId();
        String orderPaymentKey = paymentNotificationDto.paymentKey();
        Order existingOrder = orderRepository.findByIdAndPaymentKey(orderId, orderPaymentKey)
                .orElseThrow(() -> {
                    String errorMessage = String.format("Not found order with ID '%d' and payment key '%s'", orderId, orderPaymentKey);
                    log.error(errorMessage);
                    return new OrderNotFoundException(errorMessage);
                });

        if (paymentNotificationDto.status()) {
            existingOrder.setStatus(OrderStatus.PAID);
        } else {
            existingOrder.setStatus(OrderStatus.PAYMENT_ERROR);
            existingOrder.setObservations(paymentNotificationDto.observations());
        }
        orderRepository.save(existingOrder);
    }
}








