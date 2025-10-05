package io.github.joaoVitorLeal.marketsphere.orders.service.webhook.impl;

import io.github.joaoVitorLeal.marketsphere.orders.dto.webhook.PaymentCallbackRequestDto;
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
    public void updatePaymentStatus(PaymentCallbackRequestDto paymentCallbackRequestDto) {
        Long orderId = paymentCallbackRequestDto.orderId();
        String orderPaymentKey = paymentCallbackRequestDto.paymentKey();
        Order existingOrder = orderRepository.findByIdAndPaymentKey(orderId, orderPaymentKey)
                .orElseThrow(() -> {
                    String errorMessage = String.format("Not found order with ID '%d' and payment key '%s'", orderId, orderPaymentKey);
                    log.error(errorMessage);
                    return new OrderNotFoundException(errorMessage);
                });

        if (paymentCallbackRequestDto.status()) {
            existingOrder.setStatus(OrderStatus.PAID);
        } else {
            existingOrder.setStatus(OrderStatus.PAYMENT_ERROR);
            existingOrder.setObservations(paymentCallbackRequestDto.observations());
        }
        orderRepository.save(existingOrder);
    }
}








