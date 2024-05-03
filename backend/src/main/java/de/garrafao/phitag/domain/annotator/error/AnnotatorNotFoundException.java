package de.garrafao.phitag.domain.annotator.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class AnnotatorNotFoundException extends CustomRuntimeException {

    public AnnotatorNotFoundException() {
        super("Annotator not found");
    }
    
}
