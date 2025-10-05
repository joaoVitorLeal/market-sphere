package io.github.joaoVitorLeal.marketsphere.orders.dto.webhook;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Contrato para webhook de pagamento
 * Body:
 * {
 *     "orderId": "number",
 *     "paymentKey": "string",
 *     "status": "boolean"
 *     "observations": "string"
 * }
 * <br><br/>
 * Headers:
 * {
 *     "apiKey": "string"
 * }
 * */
public record PaymentCallbackRequestDto(

        @NotNull(message = "{order.id.required}")
        @Positive(message = "{order.id.positive}")
        Long orderId,

        String paymentKey,
        boolean status,
        String observations
) { }
