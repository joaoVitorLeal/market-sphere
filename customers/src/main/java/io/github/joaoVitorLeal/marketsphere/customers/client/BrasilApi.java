package io.github.joaoVitorLeal.marketsphere.customers.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "brasilapi",
        url = "feign.clients.brasilapi.base-url"
)
public interface BrasilApi {


}
