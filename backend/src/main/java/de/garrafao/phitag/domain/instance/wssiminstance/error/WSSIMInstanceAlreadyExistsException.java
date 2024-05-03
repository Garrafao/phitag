package de.garrafao.phitag.domain.instance.wssiminstance.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class WSSIMInstanceAlreadyExistsException extends CustomRuntimeException {

    public WSSIMInstanceAlreadyExistsException() {
        super("WSSIM instance already exists");
    }

    public WSSIMInstanceAlreadyExistsException(final String message) {
        super(message);
    }
    
    
}
