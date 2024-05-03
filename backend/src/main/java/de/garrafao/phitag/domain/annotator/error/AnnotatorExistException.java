package de.garrafao.phitag.domain.annotator.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class AnnotatorExistException extends CustomRuntimeException {

    public AnnotatorExistException() {
        super("Annotator exist");
    }
    
}
