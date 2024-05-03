package de.garrafao.phitag.domain.phase.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class ComputationalAnnotationException extends CustomRuntimeException {

    public ComputationalAnnotationException() {
        super("Computational annotation failed");
    }

    public ComputationalAnnotationException(final String message) {
        super(message);
    }

}
