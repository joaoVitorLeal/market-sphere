package io.github.joaoVitorLeal.marketsphere.orders.exception.client.customers;

import io.github.joaoVitorLeal.marketsphere.orders.exception.client.ClientValidationException;

public final class CustomerClientNotFoundException extends ClientValidationException {

    public CustomerClientNotFoundException() {
        super();
    }

    public CustomerClientNotFoundException(String field, String message) {
        super(field, message);
    }

    public CustomerClientNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
