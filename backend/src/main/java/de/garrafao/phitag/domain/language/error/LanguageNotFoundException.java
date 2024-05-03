package de.garrafao.phitag.domain.language.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class LanguageNotFoundException extends CustomRuntimeException {

    public LanguageNotFoundException() {
        super("Language not found");
    }
    
}
