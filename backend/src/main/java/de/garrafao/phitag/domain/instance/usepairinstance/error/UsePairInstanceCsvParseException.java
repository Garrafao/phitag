package de.garrafao.phitag.domain.instance.usepairinstance.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UsePairInstanceCsvParseException extends CustomRuntimeException {
    
    public UsePairInstanceCsvParseException() {
        super("CSV File for Use Pair Instance is not correctly formatted");
    }
}
