package de.garrafao.phitag.domain.user.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UserExistsException extends CustomRuntimeException {

    public UserExistsException() {
        super("User already exists");
    }

}
