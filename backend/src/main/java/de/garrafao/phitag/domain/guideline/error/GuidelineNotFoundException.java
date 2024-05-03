package de.garrafao.phitag.domain.guideline.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class GuidelineNotFoundException extends CustomRuntimeException {

    public GuidelineNotFoundException() {
        super("Guideline not found");
    }

}
