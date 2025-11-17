package io.github.joaoVitorLeal.marketsphere.billing.model;

public record Customer(
        String fullName,
        String nationalId,
        String email,
        String phoneNumber,
        Address address
) { }
