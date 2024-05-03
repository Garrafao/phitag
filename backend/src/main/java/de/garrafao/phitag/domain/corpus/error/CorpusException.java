package de.garrafao.phitag.domain.corpus.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class CorpusException extends CustomRuntimeException {

    public CorpusException(String message) {
        super(message);
    }

}
