package de.garrafao.phitag.domain.annotationprocessinformation.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class AnnotationProcessInformationException extends CustomRuntimeException {

    public AnnotationProcessInformationException (final String message) {
        super(message);
    }
    
}
