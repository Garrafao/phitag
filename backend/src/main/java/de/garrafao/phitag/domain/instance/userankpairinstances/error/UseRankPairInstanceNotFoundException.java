package de.garrafao.phitag.domain.instance.userankpairinstances.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UseRankPairInstanceNotFoundException extends CustomRuntimeException {

    public UseRankPairInstanceNotFoundException() {
        super("Instance not found");
    }

}
