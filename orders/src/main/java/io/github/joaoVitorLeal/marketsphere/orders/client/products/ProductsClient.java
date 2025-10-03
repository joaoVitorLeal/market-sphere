package io.github.joaoVitorLeal.marketsphere.orders.client.products;

import io.github.joaoVitorLeal.marketsphere.orders.client.products.representation.ProductRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "products",
        url = "${market-sphere.feign.clients.products.base-url}"
)
public interface ProductsClient {

    @GetMapping("/{productId}")
    ResponseEntity<ProductRepresentation> getProductById(@PathVariable Long productId);
}
