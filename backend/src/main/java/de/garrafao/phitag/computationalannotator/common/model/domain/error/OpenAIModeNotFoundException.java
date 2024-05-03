package de.garrafao.phitag.computationalannotator.common.model.domain.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class OpenAIModeNotFoundException extends CustomRuntimeException {
    
    public OpenAIModeNotFoundException() {
        super("Model not found");
    }
}
