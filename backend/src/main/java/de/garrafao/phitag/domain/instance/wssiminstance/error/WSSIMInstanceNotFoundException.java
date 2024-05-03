package de.garrafao.phitag.domain.instance.wssiminstance.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class WSSIMInstanceNotFoundException extends CustomRuntimeException {

    public WSSIMInstanceNotFoundException() {
        super("WSSIM instance not found");
    }

    public WSSIMInstanceNotFoundException(final String message) {
        super(message);
    }
    
}
