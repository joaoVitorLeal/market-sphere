package io.github.joaoVitorLeal.marketsphere.orders.service.webhook;

import io.github.joaoVitorLeal.marketsphere.orders.dto.webhook.PaymentNotificationDto;

public interface PaymentWebhookService {

    void updatePaymentStatus(PaymentNotificationDto paymentNotificationDto);
}
