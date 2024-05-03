package de.garrafao.phitag.application.joblisting.data.command;

import org.apache.commons.lang3.Validate;

import lombok.Getter;

@Getter
public class CreateJoblistingCommand {
    
    private final String name;
    private final String owner;
    private final String project;
    private final String phase;
    private final boolean open;
    private final String description;

    public CreateJoblistingCommand(final String name, final String owner, final String project, final String phase, final boolean open, final String description) {
        Validate.notBlank(name, "name must not be blank");
        
        Validate.notBlank(owner, "owner must not be blank");
        Validate.notBlank(project, "project must not be blank");
        Validate.notBlank(phase, "phase must not be blank");
        this.name = name;
        
        this.owner = owner;
        this.project = project;
        this.phase = phase;
        this.open = open;
        this.description = description;
    }

}
