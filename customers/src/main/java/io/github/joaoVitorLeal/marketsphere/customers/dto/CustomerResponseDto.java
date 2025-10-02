package io.github.joaoVitorLeal.marketsphere.customers.dto;

public record CustomerResponseDto(
        Long id,
        String fullName,
        String nationalId,
        String email,
        String phoneNumber,
        String postalCode,
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state,
        String country
) { }

