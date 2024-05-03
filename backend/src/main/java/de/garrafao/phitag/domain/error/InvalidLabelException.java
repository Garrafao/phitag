package de.garrafao.phitag.domain.error;

public class InvalidLabelException extends CustomRuntimeException {

    public InvalidLabelException() {
        super("Invalid label. Please read the guidelines");
    }

    public InvalidLabelException(String message) {
        super("Invalid label error: " + message);
    }
    
}
