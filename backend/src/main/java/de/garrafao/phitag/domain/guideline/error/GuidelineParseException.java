package de.garrafao.phitag.domain.guideline.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class GuidelineParseException extends CustomRuntimeException {

    public GuidelineParseException() {
        super("Guideline parse error");
    }

    public GuidelineParseException(String message) {
        super("Guideline parse error: " + message);
    }
    
}
