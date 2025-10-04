package io.github.joaoVitorLeal.marketsphere.orders.service.impl;

import io.github.joaoVitorLeal.marketsphere.orders.client.banking.BankingClient;
import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderRequestDto;
import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderResponseDto;
import io.github.joaoVitorLeal.marketsphere.orders.client.banking.representation.BankingPaymentRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.mapper.OrderMapper;
import io.github.joaoVitorLeal.marketsphere.orders.model.Order;
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
