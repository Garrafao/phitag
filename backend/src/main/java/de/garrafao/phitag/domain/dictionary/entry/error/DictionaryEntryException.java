package de.garrafao.phitag.domain.dictionary.entry.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class DictionaryEntryException extends CustomRuntimeException {

    public DictionaryEntryException(final String message) {
        super(message);
    }

}
