package io.github.joaoVitorLeal.marketsphere.orders.service;

import io.github.joaoVitorLeal.marketsphere.orders.client.customers.representation.CustomerRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.client.products.representation.ProductRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.exception.OrderNotFoundException;
import io.github.joaoVitorLeal.marketsphere.orders.exception.client.products.ProductClientNotFoundException;
import io.github.joaoVitorLeal.marketsphere.orders.facade.OrderDependenciesFacade;
import io.github.joaoVitorLeal.marketsphere.orders.model.Order;
import io.github.joaoVitorLeal.marketsphere.orders.model.OrderItem;
import io.github.joaoVitorLeal.marketsphere.orders.model.enums.OrderStatus;
import io.github.joaoVitorLeal.marketsphere.orders.publisher.PaymentPublisher;
import io.github.joaoVitorLeal.marketsphere.orders.publisher.event.OrderItemPayload;
import io.github.joaoVitorLeal.marketsphere.orders.publisher.event.OrderPaidEvent;
import io.github.joaoVitorLeal.marketsphere.orders.publisher.mapper.OrderItemPayloadMapper;
import io.github.joaoVitorLeal.marketsphere.orders.publisher.mapper.OrderPaidEventMapper;
import io.github.joaoVitorLeal.marketsphere.orders.repository.OrderRepository;
import io.github.joaoVitorLeal.marketsphere.orders.subscriber.event.OrderBilledEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderLifecycleServiceImpl implements OrderLifecycleService {

    // Repository
    private final OrderRepository repository;
    // Clients de Microsserviços
    private final OrderDependenciesFacade orderDependenciesFacade;
    // Mappers
    private final OrderPaidEventMapper orderPaidEventMapper;
    private final OrderItemPayloadMapper orderItemPayloadMapper;
    // Kafka Publisher
    private final PaymentPublisher paymentPublisher;

    @Transactional
    @Override
    public void processSuccessfulPayment(Long orderId) {
        Order existingOrder = this.findOrderById(orderId);

        // Atualizar status do pedido
        existingOrder.setStatus(OrderStatus.PAID);

        // Registrar o momento exato do pagamento
        existingOrder.setPaidAt(Instant.now());

        CustomerRepresentation existingCustomer = orderDependenciesFacade.getCustomerById(existingOrder.getCustomerId());
        List<OrderItemPayload> orderItemRepresentations = this.getOrderItemPayload(existingOrder);
        OrderPaidEvent orderPaidEvent = orderPaidEventMapper.toOrderEvent(existingOrder, existingCustomer, orderItemRepresentations);

        paymentPublisher.publish(orderPaidEvent);
    }

    @Transactional
    @Override
    public void processPaymentError(Order order, String observations) {
        log.warn("Processing payment failure for the order: {}", order.getId());
        order.setStatus(OrderStatus.PAYMENT_ERROR);
        order.setObservations(observations);
    }

    @Transactional
    @Override
    public void processOrderBilled(OrderBilledEvent orderBilledEvent) {
        Order existingOrder = this.findOrderById(orderBilledEvent.orderId());
        existingOrder.setStatus(OrderStatus.BILLED);
        existingOrder.setBilledAt(orderBilledEvent.billedAt());
        existingOrder.setInvoiceUrl(orderBilledEvent.invoiceUrl());
    }

    // ---- MÉTODOS PRIVADOS (helpers) ---- //

    private Order findOrderById(Long orderId) {
        return repository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    // Helper para o KAFKA (Evento)
    private List<OrderItemPayload> getOrderItemPayload(Order order) {
        List<Long> productsIds = order.getOrderItems()
                .stream()
                .map(OrderItem::getProductId)
                .toList();

        if (productsIds.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Long, ProductRepresentation> productRepresentations = orderDependenciesFacade.getProductsByIds(productsIds);

        return order.getOrderItems()
                .stream()
                .map(orderItem -> {
                    ProductRepresentation productRepresentation = productRepresentations.get(orderItem.getProductId());

                    if (productRepresentation == null) {
                        log.error(
                                "Data integrity failure: Product ID {} (from Order ID: {}) not found in 'products' service during event processing.",
                                orderItem.getProductId(), order.getId()
                        );

                        throw new ProductClientNotFoundException("productId", "Product with ID " + orderItem.getProductId() + " not found (orphaned data).");
                    }
                    return orderItemPayloadMapper.toOrderItemPayload(orderItem, productRepresentation);
                })
                .toList();
    }
}
