package de.garrafao.phitag.domain.phitagdata.usage.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UsageNotFoundException extends CustomRuntimeException {

    public UsageNotFoundException() {
        super("Usage not found");
    }

    public UsageNotFoundException(final String message) {
        super(message);
    }

}
