package io.github.joaoVitorLeal.marketsphere.orders.service.impl;

import io.github.joaoVitorLeal.marketsphere.orders.client.banking.BankingClient;
import io.github.joaoVitorLeal.marketsphere.orders.client.banking.representation.BankingPaymentRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.client.customers.CustomersClient;
import io.github.joaoVitorLeal.marketsphere.orders.client.customers.representation.CustomerRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.client.products.ProductsClient;
import io.github.joaoVitorLeal.marketsphere.orders.client.products.representation.ProductRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderRequestDto;
import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderResponseDto;
import io.github.joaoVitorLeal.marketsphere.orders.exception.OrderNotFoundException;
import io.github.joaoVitorLeal.marketsphere.orders.mapper.OrderMapper;
import io.github.joaoVitorLeal.marketsphere.orders.model.Order;
import io.github.joaoVitorLeal.marketsphere.orders.model.OrderItem;
import io.github.joaoVitorLeal.marketsphere.orders.model.PaymentInfo;
import io.github.joaoVitorLeal.marketsphere.orders.model.enums.OrderStatus;
import io.github.joaoVitorLeal.marketsphere.orders.model.enums.PaymentType;
import io.github.joaoVitorLeal.marketsphere.orders.publisher.PaymentPublisher;
import io.github.joaoVitorLeal.marketsphere.orders.publisher.mapper.OrderItemRepresentationMapper;
import io.github.joaoVitorLeal.marketsphere.orders.publisher.mapper.OrderRepresentationMapper;
import io.github.joaoVitorLeal.marketsphere.orders.publisher.representation.OrderItemRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.publisher.representation.OrderRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.repository.OrderItemRepository;
import io.github.joaoVitorLeal.marketsphere.orders.repository.OrderRepository;
import io.github.joaoVitorLeal.marketsphere.orders.service.OrderService;
import io.github.joaoVitorLeal.marketsphere.orders.validator.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final String NEW_PAYMENT_OBSERVATION_MESSAGE = "New payment made. Awaiting processing.";

    private final OrderRepository repository;
    private final OrderItemRepository orderItemRepository;
    private final OrderValidator validator;
    private final OrderMapper mapper;
    private final BankingClient bankingClient;
    private final CustomersClient customersClient;
    private final ProductsClient productsClient;
    private final OrderRepresentationMapper orderRepresentationMapper;
    private final OrderItemRepresentationMapper orderItemRepresentationMapper;
    private final PaymentPublisher paymentPublisher;


    @Transactional
    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        validator.validate(orderRequestDto);
        Order createdOrder = performPersistence(orderRequestDto);

        sendPaymentRequest(createdOrder);
        return mapper.toOrderDto(createdOrder);
    }

    @Transactional
    @Override
    public void createPayment(Long orderId, String metadata, PaymentType paymentType) {
        Order existingOrder = this.findOrderById(orderId);

        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setPaymentType(paymentType);
        paymentInfo.setMetadata(metadata);
        existingOrder.setStatus(OrderStatus.PLACED);
        existingOrder.setObservations(NEW_PAYMENT_OBSERVATION_MESSAGE);

        BankingPaymentRepresentation bankingPaymentRepresentation = bankingClient.requestPayment(existingOrder);
        existingOrder.setPaymentKey(bankingPaymentRepresentation.paymentKey());
    }

    @Transactional(readOnly = true)
    @Override
    public OrderResponseDto getOrderById(Long orderId) {
        return mapper.toOrderDto(this.findOrderById(orderId));
    }

    @Transactional(readOnly = true)
    @Override
    public OrderRepresentation getOrderRepresentationById(Long orderId) {
        Order existingOrder = this.findOrderById(orderId);
        CustomerRepresentation customerRepresentation = this.findCustomerRepresentation(existingOrder);
        List<OrderItemRepresentation> orderItemRepresentations = this.getOrderItemRepresentations(existingOrder);
        return orderRepresentationMapper.toOrderRepresentation(existingOrder, customerRepresentation, orderItemRepresentations);
    }

    @Transactional
    @Override
    public void processSuccessfulPayment(Long orderId) {
        Order existingOrder = this.findOrderById(orderId);
        existingOrder.setStatus(OrderStatus.PAID);

        CustomerRepresentation customerRepresentation = this.findCustomerRepresentation(existingOrder);
        List<OrderItemRepresentation> orderItemRepresentations = this.getOrderItemRepresentations(existingOrder);
        OrderRepresentation orderRepresentation = orderRepresentationMapper.toOrderRepresentation(existingOrder, customerRepresentation, orderItemRepresentations);

        paymentPublisher.publish(orderRepresentation);
    }

    private Order findOrderById(Long orderId) {
        return repository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    private void sendPaymentRequest(Order createdOrder) {
        // Atualizar o pedido com a chave de pagamento emitida pelo banco (simulação)
        BankingPaymentRepresentation paymentRepresentation = bankingClient.requestPayment(createdOrder);
        createdOrder.setPaymentKey(paymentRepresentation.paymentKey());
    }

    private Order performPersistence(OrderRequestDto orderRequestDto) {
        Order createdOrder = repository.save(mapper.toOrderEntity(orderRequestDto));
        orderItemRepository.saveAll(createdOrder.getOrderItems());
        return createdOrder;
    }

    private CustomerRepresentation findCustomerRepresentation(Order order) {
        return customersClient.getCustomerById(order.getCustomerId()).getBody();
    }

    private List<OrderItemRepresentation> getOrderItemRepresentations(Order order) {
        List<Long> productsIds = order.getOrderItems()
                .stream()
                .map(OrderItem::getProductId)
                .toList();

        if (productsIds.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Long, ProductRepresentation> productRepresentationMap = getProductRepresentationMap(productsIds);

        return order.getOrderItems()
                .stream()
                .map(orderItem -> {
                    ProductRepresentation productRepresentation = productRepresentationMap.get(orderItem.getProductId());
                    return orderItemRepresentationMapper.toOrderItemRepresentation(orderItem, productRepresentation);
                })
                .toList();
    }

    private Map<Long, ProductRepresentation> getProductRepresentationMap(List<Long> productsIds) {
        return Objects.requireNonNull(productsClient.getAllProductsByIds(productsIds).getBody())
                .stream()
                .collect(Collectors.toMap(ProductRepresentation::id, productRepresentation -> productRepresentation));
    }

}
