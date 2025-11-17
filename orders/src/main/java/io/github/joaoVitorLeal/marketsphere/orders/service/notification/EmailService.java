package io.github.joaoVitorLeal.marketsphere.orders.service.notification;

import io.github.joaoVitorLeal.marketsphere.orders.client.customers.representation.CustomerRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.model.Order;

/**
* Sistema de notificação por Email para clientes.
* */
public interface EmailService {
    void sendShipmentConfirmationEmail(CustomerRepresentation customer, Order order);
}
