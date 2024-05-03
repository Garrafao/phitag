package de.garrafao.phitag.domain.project.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class ProjectNameRestrictionException extends CustomRuntimeException {
    
        public ProjectNameRestrictionException() {
            super("Project cannot be of this name");
        }
    
        public ProjectNameRestrictionException(String message) {
            super(message);
        }
    
}
