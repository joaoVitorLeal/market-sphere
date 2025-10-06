package io.github.joaoVitorLeal.marketsphere.orders.service;

import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderRequestDto;
import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderResponseDto;
import io.github.joaoVitorLeal.marketsphere.orders.model.enums.PaymentType;
import io.github.joaoVitorLeal.marketsphere.orders.publisher.representation.OrderRepresentation;

public interface OrderService {

    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);
    void createPayment(Long orderId, String cardInfo, PaymentType paymentType);
    OrderResponseDto getOrderById(Long orderId);
    OrderRepresentation getOrderRepresentationById(Long orderId);
    void processSuccessfulPayment(Long orderId);
}
