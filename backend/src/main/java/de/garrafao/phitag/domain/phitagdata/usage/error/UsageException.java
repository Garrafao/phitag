package de.garrafao.phitag.domain.phitagdata.usage.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UsageException extends CustomRuntimeException {

    public UsageException(final String message) {
        super(message);
    }

}
