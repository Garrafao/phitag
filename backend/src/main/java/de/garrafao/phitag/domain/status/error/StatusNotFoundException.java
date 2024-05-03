package de.garrafao.phitag.domain.status.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class StatusNotFoundException extends CustomRuntimeException {

    public StatusNotFoundException() {
        super("Status not found");
    }

    public StatusNotFoundException(String message) {
        super(message);
    }
    
}
