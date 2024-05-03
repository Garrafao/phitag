package de.garrafao.phitag.domain.phase.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class EmptyPhaseNameException extends CustomRuntimeException {

    public EmptyPhaseNameException() {
        super("Phase name must not be empty");
    }
    
}
