package io.github.joaoVitorLeal.marketsphere.orders.facade;

import io.github.joaoVitorLeal.marketsphere.orders.client.banking.representation.BankingPaymentRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.client.customers.representation.CustomerRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.client.products.representation.ProductRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.facade.client.BankingClientService;
import io.github.joaoVitorLeal.marketsphere.orders.facade.client.CustomerClientService;
import io.github.joaoVitorLeal.marketsphere.orders.facade.client.ProductClientService;
import io.github.joaoVitorLeal.marketsphere.orders.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Implementação da Facade principal.
 * Esta classe injeta os services dos clients (que contêm a lógica e validações)
 * e delega as chamadas para eles.
 */
@Component
@RequiredArgsConstructor
public class OrderDependenciesFacadeImpl implements OrderDependenciesFacade {

    // Injeta as abstrações dos Serviços de Cliente
    private final CustomerClientService customerClientService;
    private final ProductClientService productClientService;
    private final BankingClientService bankingClientService;

    @Override
    public CustomerRepresentation getCustomerById(Long customerId) {
        return customerClientService.getCustomerById(customerId);
    }

    @Override
    public ProductRepresentation getProductById(Long productId) {
        return productClientService.getProductById(productId);
    }

    @Override
    public Map<Long, ProductRepresentation> getProductsByIds(List<Long> productIds) {
        return productClientService.getProductsByIds(productIds);
    }

    @Override
    public BankingPaymentRepresentation requestPayment(Order order) {
        return bankingClientService.requestPayment(order);
    }
}
