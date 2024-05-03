package de.garrafao.phitag.domain.instance.userankinstance.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UseRankInstanceAlreadyExistsException extends CustomRuntimeException {

    public UseRankInstanceAlreadyExistsException() {
        super("Instance already exists");
    }

    public UseRankInstanceAlreadyExistsException(final String message) {
        super("Instance already exists: " + message);
    }
    
}
