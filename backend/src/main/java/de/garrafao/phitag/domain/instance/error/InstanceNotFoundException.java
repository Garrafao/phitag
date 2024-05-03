package de.garrafao.phitag.domain.instance.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class InstanceNotFoundException extends CustomRuntimeException {

    public InstanceNotFoundException() {
        super("Instance not found");
    }

    public InstanceNotFoundException(String message) {
        super(message);
    }

}
