package io.github.joaoVitorLeal.marketsphere.orders.facade;

import io.github.joaoVitorLeal.marketsphere.orders.client.banking.representation.BankingPaymentRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.client.customers.representation.CustomerRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.client.products.representation.ProductRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.exception.client.customers.CustomerClientNotFoundException;
import io.github.joaoVitorLeal.marketsphere.orders.exception.client.products.ProductClientNotFoundException;
import io.github.joaoVitorLeal.marketsphere.orders.model.Order;

import java.util.List;
import java.util.Map;

/**
 * Facade principal e interface de alto nível para o domínio de Pedidos.
 *
 * Centraliza o acesso aos microsserviços de Produtos e Clientes,
 * além da integração com gateway de pagamentos (simulado).
 *
 * Isola o domínio da complexidade de infraestrutura, como chamadas
 * a Feign clients, tratamento de erros de rede e lógica de integração.
 */
public interface OrderDependenciesFacade {

    /**
     * Buscar dados de um cliente pelo seu ID
     *
     * @param customerId O ID do cliente
     * @return {@code CustomerRepresentation}
     * @throws CustomerClientNotFoundException Se o cliente não for encontrado
     * */
    CustomerRepresentation getCustomerById(Long customerId);

     /**
     * Busca os dados de um produto pelo seu ID.
     *
     * @param productId O ID do produto a ser buscado.
     * @return {@code ProductRepresentation}
     * @throws ProductClientNotFoundException Se o produto não for encontrado.
     */
    ProductRepresentation getProductById(Long productId);

    /**
     * Busca os dados de um conjunto de produtos por uma lista de IDs.s
     *
     * @param productIds Uma lista de IDs de produtos a serem buscados.
     * @return {@code Map<Long, ProductRepresentation>} Um <i>Map</i> dos produtos encontrados indexados pelo seu ID
     */
    Map<Long, ProductRepresentation> getProductsByIds(List<Long> productIds);

    /**
     * <strong>(Simulação)</strong> Inicia uma solicitação de pagamento para um pedido específico.
     *
     * @param order A entidade Order contendo os detalhes do pagamento.
     * @return {@code BankingPaymentRepresentation} O resultado da solicitação de pagamento.
     */
    BankingPaymentRepresentation requestPayment(Order order);
}
