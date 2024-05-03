package de.garrafao.phitag.domain.authentication.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class AccessDenidedException extends CustomRuntimeException {

    public AccessDenidedException() {
        super("Access denied to resource");
    }

    public AccessDenidedException(String message) {
        super("Access denied to resource: " + message);
    }

}
