package de.garrafao.phitag.domain.joblisting.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class JoblistingNotFoundException extends CustomRuntimeException {

    public JoblistingNotFoundException() {
        super("Joblisting not found");
    }
    
}
