package de.garrafao.phitag.domain.error;

public class CustomRuntimeException extends RuntimeException {

    CustomRuntimeException() {
        super();
    }

    public CustomRuntimeException(String message) {
        super(message);
    }

    public CustomRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
