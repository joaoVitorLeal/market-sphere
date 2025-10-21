package io.github.joaoVitorLeal.marketsphere.orders.service;

import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderDetailsResponseDto;
import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderRequestDto;
import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderResponseDto;
import io.github.joaoVitorLeal.marketsphere.orders.model.enums.PaymentType;

public interface OrderService {

    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);
    void createPayment(Long orderId, String cardInfo, PaymentType paymentType);
    OrderResponseDto getOrderById(Long orderId);
    OrderDetailsResponseDto getOrderDetailsById(Long orderId);
    void processSuccessfulPayment(Long orderId);
}
