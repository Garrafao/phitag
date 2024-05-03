package de.garrafao.phitag.domain.sampling.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class SamplingException extends CustomRuntimeException {

    public SamplingException(String message) {
        super(message);
    }
    
}
