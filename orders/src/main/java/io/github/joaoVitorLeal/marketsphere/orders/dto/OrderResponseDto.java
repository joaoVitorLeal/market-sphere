package io.github.joaoVitorLeal.marketsphere.orders.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.joaoVitorLeal.marketsphere.orders.model.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OrderResponseDto(

        Long id,
        Long customerId,
        @JsonFormat(
                shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd'T'HH:mm:ss",
                timezone = "America/Sao_Paulo"
        )
        Instant orderDate,
        String observations,
        OrderStatus status,
        BigDecimal total,
        UUID trackingCode,
        int amountItems
) { }
