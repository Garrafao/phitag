package de.garrafao.phitag.domain.user.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UserNotExistsException extends CustomRuntimeException {

    public UserNotExistsException() {
        super("User does not exist");
    }
}
