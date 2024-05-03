package de.garrafao.phitag.domain.instance.userankpairinstances.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UseRankPairInstanceAlreadyExistsException extends CustomRuntimeException {

    public UseRankPairInstanceAlreadyExistsException() {
        super("Instance already exists");
    }

    public UseRankPairInstanceAlreadyExistsException(final String message) {
        super("Instance already exists: " + message);
    }
    
}
