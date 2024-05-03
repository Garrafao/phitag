package de.garrafao.phitag.domain.instance.userankrelative.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UseRankInstanceNotFoundException extends CustomRuntimeException {

    public UseRankInstanceNotFoundException() {
        super("Instance not found");
    }

}
