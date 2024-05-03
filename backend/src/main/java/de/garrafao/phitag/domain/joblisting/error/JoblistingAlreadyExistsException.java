package de.garrafao.phitag.domain.joblisting.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class JoblistingAlreadyExistsException extends CustomRuntimeException {

    public JoblistingAlreadyExistsException() {
        super("Joblisting already exists");
    }
    
}
