package io.github.joaoVitorLeal.marketsphere.orders.exception;

/**
 * Exceção lançada quando ocorre um erro de processamento
 * de pedidos enviados.
 */
public class OrderShippedProcessingException extends RuntimeException {
    public OrderShippedProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
