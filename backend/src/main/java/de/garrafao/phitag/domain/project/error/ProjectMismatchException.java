package de.garrafao.phitag.domain.project.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class ProjectMismatchException extends CustomRuntimeException {
    
    public ProjectMismatchException() {
        super("Project mismatch");
    }
}
