package io.github.joaoVitorLeal.marketsphere.orders.facade.client;

import io.github.joaoVitorLeal.marketsphere.orders.client.banking.representation.BankingPaymentRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.model.Order;

/**
 * Interface para o gateway de pagamentos (Mock).
 */
public interface BankingClientService {

    /**
     * Solicita um pagamento (atualmente simulado).
     *
     * @param order O pedido para o qual o pagamento está sendo solicitado.
     * @return {@link BankingPaymentRepresentation} com o resultado da solicitação.
     */
    BankingPaymentRepresentation requestPayment(Order order);
}
