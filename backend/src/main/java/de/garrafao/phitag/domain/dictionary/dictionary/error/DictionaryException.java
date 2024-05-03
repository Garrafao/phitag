package de.garrafao.phitag.domain.dictionary.dictionary.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class DictionaryException extends CustomRuntimeException {

    public DictionaryException(final String message) {
        super(message);
    }

}