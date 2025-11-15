package io.github.joaoVitorLeal.marketsphere.orders.client.banking;

import io.github.joaoVitorLeal.marketsphere.orders.client.banking.representation.BankingPaymentRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.model.Order;

public interface BankingClient {

    /**
     * Simula uma solicitação de pagamento a um gateway bancário.
     *
     * @param order o pedido a ser pago
     * @return representação contendo chave de pagamento, status, mensagem e timestamp
     * */
    BankingPaymentRepresentation requestPayment(Order order);
}
