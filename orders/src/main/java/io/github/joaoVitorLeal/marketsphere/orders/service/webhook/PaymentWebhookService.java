package io.github.joaoVitorLeal.marketsphere.orders.service.webhook;

import io.github.joaoVitorLeal.marketsphere.orders.dto.webhook.PaymentCallbackRequestDto;

public interface PaymentWebhookService {

    void updatePaymentStatus(PaymentCallbackRequestDto paymentCallbackRequestDto);
}
