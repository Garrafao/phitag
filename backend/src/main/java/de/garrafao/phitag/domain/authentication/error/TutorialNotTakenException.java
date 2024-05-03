package de.garrafao.phitag.domain.authentication.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class TutorialNotTakenException extends CustomRuntimeException {
    public TutorialNotTakenException(String message) {
        super(message);
    }


}
