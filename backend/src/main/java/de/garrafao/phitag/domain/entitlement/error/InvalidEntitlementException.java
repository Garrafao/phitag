package de.garrafao.phitag.domain.entitlement.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class InvalidEntitlementException extends CustomRuntimeException {

    public InvalidEntitlementException() {
        super("Invalid entitlement");
    }

    public InvalidEntitlementException(final String message) {
        super("Invalid entitlement:" + message);
    }
    
}
