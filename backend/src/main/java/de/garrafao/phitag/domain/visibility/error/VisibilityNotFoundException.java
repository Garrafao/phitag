package de.garrafao.phitag.domain.visibility.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class VisibilityNotFoundException extends CustomRuntimeException {

    public VisibilityNotFoundException() {
        super("Visibility does not exist");
    }

}
