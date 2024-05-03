package de.garrafao.phitag.domain.project.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class ProjectNotExistsException extends CustomRuntimeException {
    
    public ProjectNotExistsException() {
        super("Project not found");
    }
    
}
