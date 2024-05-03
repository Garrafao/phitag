package de.garrafao.phitag.domain.sampling.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class SamplingNotFoundException extends CustomRuntimeException {

    public SamplingNotFoundException() {
        super("Sampling not found");
    }

    public SamplingNotFoundException(final String message) {
        super(message);
    }
    
}
