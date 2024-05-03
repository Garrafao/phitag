package de.garrafao.phitag.domain.instance.wssimtag.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class WSSIMTagNotFoundException extends CustomRuntimeException {

    public WSSIMTagNotFoundException() {
        super("WSSIMTag not found");
    }

    public WSSIMTagNotFoundException(final String message) {
        super(message);
    }

}
