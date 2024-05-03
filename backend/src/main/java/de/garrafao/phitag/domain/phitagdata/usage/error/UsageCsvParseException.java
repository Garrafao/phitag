package de.garrafao.phitag.domain.phitagdata.usage.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UsageCsvParseException extends CustomRuntimeException {
    
    public UsageCsvParseException() {
        super("CSV File for Usages is not correctly formatted");
    }
}
