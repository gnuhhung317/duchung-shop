package net.duchung.shop_app.exception;

public class SizeLimitExceededException extends RuntimeException {
    public SizeLimitExceededException(String message) {
        super(message);
    }
}
