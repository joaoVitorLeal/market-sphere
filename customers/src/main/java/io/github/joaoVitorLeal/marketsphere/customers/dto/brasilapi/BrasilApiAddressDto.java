package io.github.joaoVitorLeal.marketsphere.customers.dto.brasilapi;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BrasilApiAddressDto(

        @JsonAlias("cep")
        String postalCode,
        String state,
        String city,
        String neighborhood,
        String street
) { }
