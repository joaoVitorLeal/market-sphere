package io.github.joaoVitorLeal.marketsphere.orders.model;

import io.github.joaoVitorLeal.marketsphere.orders.model.enums.PaymentType;
import lombok.Data;

@Data
public class PaymentInfo {
    private String metadata;
    private PaymentType paymentType;
}
