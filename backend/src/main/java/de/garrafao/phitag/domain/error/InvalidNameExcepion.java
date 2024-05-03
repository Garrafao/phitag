package de.garrafao.phitag.domain.error;

public class InvalidNameExcepion extends CustomRuntimeException {

    public InvalidNameExcepion() {
        super("Invalid name");
    }

    public InvalidNameExcepion(String message) {
        super("Invalid name: " + message);
    }
    
    
}
