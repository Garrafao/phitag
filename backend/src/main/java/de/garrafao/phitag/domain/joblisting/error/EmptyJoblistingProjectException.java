package de.garrafao.phitag.domain.joblisting.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class EmptyJoblistingProjectException extends CustomRuntimeException {

    public EmptyJoblistingProjectException() {
        super("Joblisting project must not be empty");
    }
    
}
