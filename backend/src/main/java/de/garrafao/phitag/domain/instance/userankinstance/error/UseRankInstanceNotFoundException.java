package de.garrafao.phitag.domain.instance.userankinstance.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UseRankInstanceNotFoundException extends CustomRuntimeException {

    public UseRankInstanceNotFoundException() {
        super("Instance not found");
    }

}
