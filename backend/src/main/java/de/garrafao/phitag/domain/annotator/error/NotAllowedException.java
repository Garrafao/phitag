package de.garrafao.phitag.domain.annotator.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class NotAllowedException extends CustomRuntimeException {

    public NotAllowedException () {
        super("Owner of the project cannot be removed");
    }

}
