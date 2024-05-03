package de.garrafao.phitag.domain.joblisting.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class JoblistingException extends CustomRuntimeException {

    public JoblistingException() {
        super("Joblisting error");
    }

    public JoblistingException(String message) {
        super("Joblisting error: " + message);
    }
    
}
