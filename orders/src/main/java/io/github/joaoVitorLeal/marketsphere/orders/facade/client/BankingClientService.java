package io.github.joaoVitorLeal.marketsphere.orders.facade.client;

import io.github.joaoVitorLeal.marketsphere.orders.client.banking.representation.BankingPaymentRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.model.Order;

/**
 * Interface para o serviço do client de Banking.
 * Abstrai a forma como a solicitação de pagamento é feita.
 */
public interface BankingClientService {

    /**
     * Solicita um pagamento (atualmente simulado).
     * @param order O pedido para o qual o pagamento está sendo solicitado.
     * @return A representação da resposta do pagamento.
     */
    BankingPaymentRepresentation requestPayment(Order order);
}
