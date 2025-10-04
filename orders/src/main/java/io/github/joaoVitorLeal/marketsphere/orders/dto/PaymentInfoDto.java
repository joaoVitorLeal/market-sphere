package io.github.joaoVitorLeal.marketsphere.orders.dto;

import io.github.joaoVitorLeal.marketsphere.orders.model.enums.PaymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PaymentInfoDto(

        @NotBlank(message = "{order.paymentInfo.metadata.required}")
        String metadata,

        @NotNull(message = "{order.paymentInfo.paymentType.required}")
        PaymentType paymentType
) { }
