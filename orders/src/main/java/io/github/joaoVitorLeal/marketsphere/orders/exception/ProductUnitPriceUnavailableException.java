package io.github.joaoVitorLeal.marketsphere.orders.exception;

public class ProductUnitPriceUnavailableException extends RuntimeException {

    public ProductUnitPriceUnavailableException(String message) {
        super(message);
    }

    public ProductUnitPriceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
