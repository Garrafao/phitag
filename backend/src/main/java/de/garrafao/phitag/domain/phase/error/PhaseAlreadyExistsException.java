package de.garrafao.phitag.domain.phase.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class PhaseAlreadyExistsException extends CustomRuntimeException {

    public PhaseAlreadyExistsException() {
        super("Phase already exists");
    }
}
