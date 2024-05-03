package de.garrafao.phitag.domain.instance.usepairinstance.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UsePairInstanceAlreadyExistsException extends CustomRuntimeException {

    public UsePairInstanceAlreadyExistsException() {
        super("Instance already exists");
    }

    public UsePairInstanceAlreadyExistsException(final String message) {
        super("Instance already exists: " + message);
    }
    
}
