package de.garrafao.phitag.domain.tasks.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class TaskNotFoundException extends CustomRuntimeException {

    public TaskNotFoundException() {
        super("Task not found");
    }

    public TaskNotFoundException(String message) {
        super(message);
    }
    
}
