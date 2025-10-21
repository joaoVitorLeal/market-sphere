package io.github.joaoVitorLeal.marketsphere.orders.mapper.provider;

import io.github.joaoVitorLeal.marketsphere.orders.client.products.ProductsClient;
import io.github.joaoVitorLeal.marketsphere.orders.client.products.representation.ProductRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.exception.ProductUnitPriceUnavailableException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ProductPriceProvider {

    private final ProductsClient productsClient;

    @Named("fetchProductUnitPrice")
    public BigDecimal fetchProductUnitPrice(Long productId) {
        if (productId == null) {
            throw new ProductUnitPriceUnavailableException("Product ID cannot be null.");
        }
        try {
            ResponseEntity<ProductRepresentation> response = productsClient.getProductById(productId);

            var product = response.getBody();
            if (product == null){
                throw new ProductUnitPriceUnavailableException("Product client returned no data for ID: " + productId);
            }

            if (product.unitPrice() == null) {
                throw new ProductUnitPriceUnavailableException("Product price field is null for ID: " + productId);
            }

            return product.unitPrice();
        } catch (Exception e) {
            throw new ProductUnitPriceUnavailableException(
                    "Failed to retrieve price for product ID: " + productId + ". See cause.",
                    e
            );
        }
    }
}
