package net.duchung.shop_app.exception;

public class ImageNotFoundException extends DataNotFoundException {
    public static final String notFoundImageUrl = "404-error.jpg";
    public ImageNotFoundException(String message) {
        super(message);
    }
}
