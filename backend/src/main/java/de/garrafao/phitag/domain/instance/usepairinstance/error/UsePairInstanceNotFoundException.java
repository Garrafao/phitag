package de.garrafao.phitag.domain.instance.usepairinstance.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UsePairInstanceNotFoundException extends CustomRuntimeException {

    public UsePairInstanceNotFoundException() {
        super("Instance not found");
    }

}
