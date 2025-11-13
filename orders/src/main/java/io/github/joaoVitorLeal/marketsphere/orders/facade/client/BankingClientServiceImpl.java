package io.github.joaoVitorLeal.marketsphere.orders.facade.client;

import io.github.joaoVitorLeal.marketsphere.orders.client.banking.BankingClient;
import io.github.joaoVitorLeal.marketsphere.orders.client.banking.representation.BankingPaymentRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankingClientServiceImpl implements BankingClientService {

    private final BankingClient bankingClient;

    @Override
    public BankingPaymentRepresentation requestPayment(Order order) {
        // Por se tratar de um mock, apenas é delegado
        return bankingClient.requestPayment(order);
    }
}
