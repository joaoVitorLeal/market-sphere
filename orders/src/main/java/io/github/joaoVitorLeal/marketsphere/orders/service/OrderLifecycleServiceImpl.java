package io.github.joaoVitorLeal.marketsphere.orders.service;

import io.github.joaoVitorLeal.marketsphere.orders.client.customers.CustomersClient;
import io.github.joaoVitorLeal.marketsphere.orders.client.customers.representation.CustomerRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.client.products.ProductsClient;
import io.github.joaoVitorLeal.marketsphere.orders.client.products.representation.ProductRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.exception.OrderNotFoundException;
import io.github.joaoVitorLeal.marketsphere.orders.exception.client.customers.CustomerClientNotFoundException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderLifecycleServiceImpl implements OrderLifecycleService {

    // Repository
    private final OrderRepository repository;
    // Clients de Microsserviços
    private final CustomersClient customersClient;
    private final ProductsClient productsClient;
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

        CustomerRepresentation existingCustomer = this.findCustomerRepresentation(existingOrder.getCustomerId());
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

    private CustomerRepresentation findCustomerRepresentation(Long customerId) {
        ResponseEntity<CustomerRepresentation> response = customersClient.getCustomerById(customerId);
        return Optional.ofNullable(response.getBody())
                .orElseThrow( () -> {
                    log.error("Customer service returned a null body (200 OK) for customerId: {}.", customerId);
                    return new CustomerClientNotFoundException("customerId", "Customer not found or returned an empty response for ID: " + customerId);
                });
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

        Map<Long, ProductRepresentation> productRepresentationMap = this.getProductRepresentationMap(productsIds);

        return order.getOrderItems()
                .stream()
                .map(orderItem -> {
                    ProductRepresentation productRepresentation = productRepresentationMap.get(orderItem.getProductId());
                    return orderItemPayloadMapper.toOrderItemPayload(orderItem, productRepresentation);
                })
                .toList();
    }

    // Buscar produtos por lista de IDs
    private Map<Long, ProductRepresentation> getProductRepresentationMap(List<Long> productsIds) {
        return Optional.ofNullable(productsClient.getAllProductsByIds(productsIds).getBody())
                .orElse(Collections.emptyList())
                .stream()
                .collect(Collectors.toMap(
                        ProductRepresentation::id,
                        productRepresentation -> productRepresentation
                ));
    }
}
