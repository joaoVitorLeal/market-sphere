package io.github.joaoVitorLeal.marketsphere.orders.client.banking.representation;

import java.time.Instant;

/**
 * Representa a resposta do gateway bancário/de pagamento.
 */
public record BankingPaymentRepresentation(
    String paymentKey,    // Chave de pagamento simulada
    Integer statusCode, // Status HTTP simulado
    String message,     // Mensagem de mock
    Instant dateTime    // Timestamp da simulação
) { }
