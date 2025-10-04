package io.github.joaoVitorLeal.marketsphere.orders.controller;

import io.github.joaoVitorLeal.marketsphere.orders.controller.util.HeaderLocationBuilder;
import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderRequestDto;
import io.github.joaoVitorLeal.marketsphere.orders.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        return ResponseEntity
                .created(HeaderLocationBuilder.build(service.createOrder(orderRequestDto).id()))
                .build();
    }
}
