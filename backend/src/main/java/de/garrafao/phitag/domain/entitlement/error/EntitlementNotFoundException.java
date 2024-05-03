package de.garrafao.phitag.domain.entitlement.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class EntitlementNotFoundException extends CustomRuntimeException {

    public EntitlementNotFoundException() {
        super("Entitlement not found");
    }

}
