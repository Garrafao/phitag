package de.garrafao.phitag.domain.joblisting.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class JoblistingAlreadyExistsForProjectException extends CustomRuntimeException {

    public JoblistingAlreadyExistsForProjectException() {
        super("Joblisting exists for Project");
    }
    
}
