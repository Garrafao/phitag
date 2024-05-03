package de.garrafao.phitag.domain.phase.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class PhaseNameRestrictionException extends CustomRuntimeException {

    public PhaseNameRestrictionException() {
        super("Phase cannot be of this name");
    }

    public PhaseNameRestrictionException(String message) {
        super(message);
    }


    
}
