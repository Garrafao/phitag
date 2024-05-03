package de.garrafao.phitag.domain.instance.userankrelative.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UseRankInstanceCsvParseException extends CustomRuntimeException {
    
    public UseRankInstanceCsvParseException() {
        super("CSV File for Use Pair Instance is not correctly formatted");
    }
}
