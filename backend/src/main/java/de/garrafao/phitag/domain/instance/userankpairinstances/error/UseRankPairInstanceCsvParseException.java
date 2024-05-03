package de.garrafao.phitag.domain.instance.userankpairinstances.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UseRankPairInstanceCsvParseException extends CustomRuntimeException {
    
    public UseRankPairInstanceCsvParseException() {
        super("CSV File for Use Pair Instance is not correctly formatted");
    }
}
