package de.garrafao.phitag.domain.project.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class ProjectCreationException extends CustomRuntimeException {

    public ProjectCreationException() {
        super("Exception during project creation");
    }
    
}
