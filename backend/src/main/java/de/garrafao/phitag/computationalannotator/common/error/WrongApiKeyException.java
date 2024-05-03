package de.garrafao.phitag.computationalannotator.common.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class WrongApiKeyException extends CustomRuntimeException {

    public WrongApiKeyException() {
        super("Incorrect Api key provided");
    }
}
