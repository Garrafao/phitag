package de.garrafao.phitag.domain.phase.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class TutorialException extends CustomRuntimeException {

    public TutorialException(final String message) {
        super(message);
    }
    
}
