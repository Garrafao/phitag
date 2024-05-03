package de.garrafao.phitag.domain.phitagdata.usage.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UsageExistsException extends CustomRuntimeException {

    public UsageExistsException() {
        super("Usage already exists");
    }
    
}
