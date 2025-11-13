package io.github.joaoVitorLeal.marketsphere.orders.facade.client;

import io.github.joaoVitorLeal.marketsphere.orders.client.customers.representation.CustomerRepresentation;

public interface CustomerClientService {

    /**
     * Busca um cliente pelo ID.
     * Garante que o cliente existe e é válido.
     * Trata FeignException (404) e respostas 200 OK com body nulo.
     * @throws  CustomerClientNotFoundException se o cliente não for encontrado.
     * */
    CustomerRepresentation getCustomerById(Long customerId);
}
