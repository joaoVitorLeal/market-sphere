package io.github.joaoVitorLeal.marketsphere.orders.exception.client.products;

import io.github.joaoVitorLeal.marketsphere.orders.exception.client.ClientValidationException;

public final class ProductClientNotFoundException extends ClientValidationException {

    public ProductClientNotFoundException() {
        super();
    }

    public ProductClientNotFoundException(String field, String message) {
        super(field, message);
    }

    public ProductClientNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
