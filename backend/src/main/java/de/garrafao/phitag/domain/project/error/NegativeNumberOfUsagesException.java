package de.garrafao.phitag.domain.project.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class NegativeNumberOfUsagesException extends CustomRuntimeException {
    
    public NegativeNumberOfUsagesException() {
        super("Number of usages can not be zero or negative");
    }
}
