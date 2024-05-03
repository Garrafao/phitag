package de.garrafao.phitag.domain.phase.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class PhaseNotFoundException extends CustomRuntimeException {

    public PhaseNotFoundException() {
        super("Phase not found");
    }

}
