package de.garrafao.phitag.domain.language.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class EmptyLanguageSelectionException extends CustomRuntimeException {

    public EmptyLanguageSelectionException() {
        super("Set of languages must not be empty");
    }
    
}
