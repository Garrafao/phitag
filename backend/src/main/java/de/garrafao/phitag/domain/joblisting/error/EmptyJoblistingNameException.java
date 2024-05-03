package de.garrafao.phitag.domain.joblisting.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class EmptyJoblistingNameException extends CustomRuntimeException {

    public EmptyJoblistingNameException() {
        super("Joblisting name must not be empty");
    }
    
}
