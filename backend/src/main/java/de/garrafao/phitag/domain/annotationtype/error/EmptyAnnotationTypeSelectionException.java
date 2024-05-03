package de.garrafao.phitag.domain.annotationtype.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class EmptyAnnotationTypeSelectionException extends CustomRuntimeException {

    public EmptyAnnotationTypeSelectionException() {
        super("No annotation types selected");
    }
    
}
