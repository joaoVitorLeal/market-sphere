package io.github.joaoVitorLeal.marketsphere.orders.exception.client;

import lombok.Getter;

@Getter
public abstract class ClientValidationException extends RuntimeException {

    private final String field;

    public ClientValidationException(String field, String message) {
        super(message);
        this.field = field;
    }

    public ClientValidationException(String message, Throwable cause) {
        super(message, cause);
        this.field = null;
    }
}
