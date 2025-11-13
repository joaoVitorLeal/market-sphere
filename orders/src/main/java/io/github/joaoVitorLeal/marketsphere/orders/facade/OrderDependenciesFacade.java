package io.github.joaoVitorLeal.marketsphere.orders.facade;

import io.github.joaoVitorLeal.marketsphere.orders.client.banking.representation.BankingPaymentRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.client.customers.representation.CustomerRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.client.products.representation.ProductRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.model.Order;

import java.util.List;
import java.util.Map;

/**
 * Facade principal.
 * Atua como um ponto de entrada único para todas as dependências
 * de microsserviços externos.
 *
 * Esta camada isola completamente a lógica de domínio
 * da complexidade de lidar com clientes Feign, tratamento de erros de rede
 * e lógica.
 */
public interface OrderDependenciesFacade {

    CustomerRepresentation getCustomerRepresentationById(Long customerId);
    ProductRepresentation getProductRepresentationById(Long productId);
    Map<Long, ProductRepresentation> getProductRepresentationMap(List<Long> productIds);
    BankingPaymentRepresentation requestPayment(Order order);
}
