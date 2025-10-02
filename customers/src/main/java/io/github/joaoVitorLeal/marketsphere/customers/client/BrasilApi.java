package io.github.joaoVitorLeal.marketsphere.customers.client;

import io.github.joaoVitorLeal.marketsphere.customers.client.config.BrasilApiFeignConfig;
import io.github.joaoVitorLeal.marketsphere.customers.dto.brasilapi.BrasilApiAddressDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "brasilapi",
        url = "feign.clients.brasilapi.base-url"
)
public interface BrasilApi {

    @GetMapping(
            value = "/cep/v1/{brazilianPostalCode}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    BrasilApiAddressDto getAddressDataByPostalCode(@PathVariable String brazilianPostalCode);
}
