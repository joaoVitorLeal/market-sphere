package io.github.joaoVitorLeal.marketsphere.orders.service;

import io.github.joaoVitorLeal.marketsphere.orders.model.Order;
import io.github.joaoVitorLeal.marketsphere.orders.subscriber.event.OrderBilledEvent;

/**
 * Serviço responsável por gerenciar o ciclo de vida de um Pedido.
 *
 * Esta interface lida especificamente com as transições de estado que
 * são iniciadas por eventos assíncronos como mensagens do Kafka,
 * notificações de Webhook.
 */
public interface OrderLifecycleService {

    /**
     * Processa um evento de pagamento bem-sucedido.
     * Altera o status do pedido para PAID e publica o próximo evento.
     *
     * @param orderId O ID do pedido que foi pago.
     */
    void processSuccessfulPayment(Long orderId);

    /**
     * Processa um evento de falha no pagamento.
     * Altera o status do pedido para PAYMENT_ERROR.
     *
     * @param order O pedido que falhou.
     * @param observations Detalhes da falha.
     */
    void processPaymentError(Order order, String observations);

    /**
     * Processa um evento de pedido faturado (ex: vindo do Kafka).
     * Altera o status do pedido para BILLED.
     *
     * @param orderBilledEvent O evento contendo os dados do faturamento.
     */
    void processOrderBilled(OrderBilledEvent orderBilledEvent);
}
