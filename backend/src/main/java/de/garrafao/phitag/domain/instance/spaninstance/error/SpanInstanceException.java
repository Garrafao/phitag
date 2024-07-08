package de.garrafao.phitag.domain.instance.spaninstance.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class SpanInstanceException extends CustomRuntimeException {

    public SpanInstanceException(final String message) {
        super(message);
    }

}
