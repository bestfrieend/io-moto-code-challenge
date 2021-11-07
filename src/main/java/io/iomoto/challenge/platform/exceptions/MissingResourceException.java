package io.iomoto.challenge.platform.exceptions;

public class MissingResourceException extends RuntimeException {
    public MissingResourceException(String message) {
        super(message);
    }
}
