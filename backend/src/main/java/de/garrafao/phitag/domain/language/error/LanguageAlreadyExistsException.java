package de.garrafao.phitag.domain.language.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class LanguageAlreadyExistsException extends CustomRuntimeException {

    public LanguageAlreadyExistsException() {
        super("Language already exists");
    }
    
}
