package de.garrafao.phitag.domain.visibility.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class EmptyVisibilitySelectionException extends CustomRuntimeException {
    
    public EmptyVisibilitySelectionException() {
        super("No visibilities selected");
    }
    
}
