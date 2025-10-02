package io.github.joaoVitorLeal.marketsphere.orders.service.impl;

import io.github.joaoVitorLeal.marketsphere.orders.repository.OrderItemRepository;
import io.github.joaoVitorLeal.marketsphere.orders.repository.OrderRepository;
import io.github.joaoVitorLeal.marketsphere.orders.service.OrderService;
import io.github.joaoVitorLeal.marketsphere.orders.validator.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final OrderItemRepository orderItemRepository;
    private final OrderValidator validator;

}
