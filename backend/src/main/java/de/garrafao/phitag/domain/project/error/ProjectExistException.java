package de.garrafao.phitag.domain.project.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class ProjectExistException extends CustomRuntimeException {

    public ProjectExistException() {
        super("Project already exists with these unique identifiers");
    }

}
