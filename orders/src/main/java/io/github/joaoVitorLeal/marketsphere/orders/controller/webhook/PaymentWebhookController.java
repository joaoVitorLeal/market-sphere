package io.github.joaoVitorLeal.marketsphere.orders.controller.webhook;

import io.github.joaoVitorLeal.marketsphere.orders.dto.webhook.PaymentCallbackRequestDto;
import io.github.joaoVitorLeal.marketsphere.orders.service.webhook.PaymentWebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders/payment-callbacks")
@RequiredArgsConstructor
public class PaymentWebhookController {

    private final PaymentWebhookService paymentWebhookService;

    @PostMapping
    public ResponseEntity<Void> updatePaymentStatus(
            @RequestBody PaymentCallbackRequestDto paymentCallbackRequestDto,
            @RequestHeader("apiKey") String apiKey
    ) {
        paymentWebhookService.updatePaymentStatus(paymentCallbackRequestDto);
        return ResponseEntity.noContent().build();
    }
}
