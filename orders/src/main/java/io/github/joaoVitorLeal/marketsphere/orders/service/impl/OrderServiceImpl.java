package io.github.joaoVitorLeal.marketsphere.orders.service.impl;

import io.github.joaoVitorLeal.marketsphere.orders.client.banking.BankingClient;
import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderRequestDto;
import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderResponseDto;
import io.github.joaoVitorLeal.marketsphere.orders.client.banking.representation.BankingPaymentRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.exception.OrderNotFoundException;
import io.github.joaoVitorLeal.marketsphere.orders.mapper.OrderMapper;
import io.github.joaoVitorLeal.marketsphere.orders.model.Order;
import io.github.joaoVitorLeal.marketsphere.orders.model.PaymentInfo;
import io.github.joaoVitorLeal.marketsphere.orders.model.enums.OrderStatus;
import io.github.joaoVitorLeal.marketsphere.orders.model.enums.PaymentType;
import io.github.joaoVitorLeal.marketsphere.orders.repository.OrderItemRepository;
import io.github.joaoVitorLeal.marketsphere.orders.repository.OrderRepository;
import io.github.joaoVitorLeal.marketsphere.orders.service.OrderService;
import io.github.joaoVitorLeal.marketsphere.orders.validator.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final String CREATED_NEW_PAYMENT_OBSERVATION_MESSAGE = "New payment made. Awaiting processing.";

    private final OrderRepository repository;
    private final OrderItemRepository orderItemRepository;
    private final OrderValidator validator;
    private final OrderMapper mapper;
    private final BankingClient bankingClient;

    @Transactional
    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        validator.validate(orderRequestDto);
        Order createdOrder = performPersistence(orderRequestDto);

        sendPaymentRequest(createdOrder);
        return new OrderResponseDto(createdOrder.getId());
    }

    @Transactional
    @Override
    public void createPayment(Long orderId, String metadata, PaymentType paymentType) {
        Order existingOrder = repository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setPaymentType(paymentType);
        paymentInfo.setMetadata(metadata);
        existingOrder.setStatus(OrderStatus.PLACED);
        existingOrder.setObservations(CREATED_NEW_PAYMENT_OBSERVATION_MESSAGE);

        BankingPaymentRepresentation bankingPaymentRepresentation = bankingClient.requestPayment(existingOrder);
        existingOrder.setPaymentKey(bankingPaymentRepresentation.paymentKey());
    }

    @Transactional(readOnly = true)
    @Override
    public OrderResponseDto getOrderById(Long orderId) {
        return repository.findById(orderId)
                .map(mapper::toOrderDto)
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
}
