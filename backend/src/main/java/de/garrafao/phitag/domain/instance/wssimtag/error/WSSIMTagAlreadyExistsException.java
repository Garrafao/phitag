package de.garrafao.phitag.domain.instance.wssimtag.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class WSSIMTagAlreadyExistsException extends CustomRuntimeException {

    public WSSIMTagAlreadyExistsException() {
        super("WSSIMTag already exists");
    }

    public WSSIMTagAlreadyExistsException(final String message) {
        super(message);
    }
    
}
