package io.github.joaoVitorLeal.marketsphere.orders.client.banking;

import io.github.joaoVitorLeal.marketsphere.orders.client.banking.representation.BankingPaymentRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@Slf4j
public class BankingClient {

    /**
     * Simula uma solicitação de pagamento a um gateway bancário.
     * Atualmente retorna uma resposta mockada com:
     * - String aleatória como chave de pagamento
     * - HTTP successful 200 OK
     * - Mensagem de sucesso genérica
     * - Timestamp atual
     *
     * @param order o pedido a ser pago
     * @return PaymentResponseDto contendo chave de pagamento, successful, mensagem e timestamp
     */
    public BankingPaymentRepresentation requestPayment(Order order) {
        log.info("Requesting payment for order ID {}.", order.getId());
        return new BankingPaymentRepresentation(
                UUID.randomUUID().toString(),
                HttpStatus.OK.value(),
                "Payment request simulated successfully",
                Instant.now()
        );
    }
}
