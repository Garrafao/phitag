package de.garrafao.phitag.domain.user.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UsernameRestrictionException extends CustomRuntimeException {

    public UsernameRestrictionException() {
        super("Username cannot be of this name");
    }

    public UsernameRestrictionException(String message) {
        super(message);
    }
    
}
