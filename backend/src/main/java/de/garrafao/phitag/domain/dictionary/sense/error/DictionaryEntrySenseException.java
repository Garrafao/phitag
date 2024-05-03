package de.garrafao.phitag.domain.dictionary.sense.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class DictionaryEntrySenseException extends CustomRuntimeException {

    public DictionaryEntrySenseException(final String message) {
        super(message);
    }
    
}
