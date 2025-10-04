package io.github.joaoVitorLeal.marketsphere.customers.client.brasilapi.representation;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BrasilApiAddressRepresentation(

        @JsonAlias("cep")
        String postalCode,
        String state,
        String city,
        String neighborhood,
        String street
) { }
