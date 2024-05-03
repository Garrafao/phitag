package de.garrafao.phitag.domain.usecase.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UsecaseException extends CustomRuntimeException {

    public UsecaseException(final String message) {
        super(message);
    }

}
