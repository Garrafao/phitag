package de.garrafao.phitag.domain.phase.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class PhaseUpdateException extends CustomRuntimeException {

    public PhaseUpdateException() {
        super("Phase could not be updated");
    }

    public PhaseUpdateException(final String message) {
        super("Phase could not be updated: " + message);
    }
    
}
