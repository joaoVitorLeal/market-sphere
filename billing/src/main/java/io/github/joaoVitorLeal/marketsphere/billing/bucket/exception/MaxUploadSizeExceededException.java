package io.github.joaoVitorLeal.marketsphere.billing.bucket.exception;

public class MaxUploadSizeExceededException extends RuntimeException {
    public MaxUploadSizeExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
