package de.garrafao.phitag.domain.user.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class InvalidEmailException extends CustomRuntimeException {

    public InvalidEmailException() {
        super("Invalid email address");
    }

}
