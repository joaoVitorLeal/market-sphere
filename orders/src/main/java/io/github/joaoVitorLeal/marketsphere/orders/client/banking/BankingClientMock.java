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
public class BankingClientMock implements BankingClient {

    /**
     * Simula uma solicitação de pagamento a um gateway bancário.
     * Retorna uma resposta mockada com:
     * - String aleatória como chave de pagamento
     * - HTTP successful 200 OK
     * - Mensagem de sucesso genérica
     * - Timestamp atual
     *
     * @param order o pedido a ser pago
     * @return {@link BankingPaymentRepresentation} contendo chave de pagamento, status, mensagem e timestamp
     */
    @Override
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
