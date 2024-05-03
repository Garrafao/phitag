package de.garrafao.phitag.domain.annotationtype.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class AnnotationTypeNotFoundException extends CustomRuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public AnnotationTypeNotFoundException() {
        super("AnnotationType not found");
    }
}
