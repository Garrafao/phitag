package de.garrafao.phitag.application.joblisting.data.command;

import org.apache.commons.lang3.Validate;

import lombok.Getter;

@Getter
public class JoinJoblistingCommand {
    
    private final String name;
    private final String owner;
    private final String project;

    public JoinJoblistingCommand(final String name, final String owner, final String project) {
        Validate.notBlank(name, "name must not be blank");
        Validate.notBlank(owner, "owner must not be blank");
        Validate.notBlank(project, "project must not be blank");
        
        this.name = name;
        this.owner = owner;
        this.project = project;
    }
}
