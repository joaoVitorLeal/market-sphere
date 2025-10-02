package io.github.joaoVitorLeal.marketsphere.customers.exception.brasilapi;

public class PostalCodeInvalidException extends RuntimeException {
    public PostalCodeInvalidException() {
        super("Invalid postal code.");
    }

    public PostalCodeInvalidException(String message) {
        super(message);
    }

    public PostalCodeInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
