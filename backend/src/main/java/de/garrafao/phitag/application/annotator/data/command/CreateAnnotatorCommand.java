package de.garrafao.phitag.application.annotator.data.command;

import org.apache.commons.lang3.Validate;

import lombok.Getter;

@Getter
public class CreateAnnotatorCommand {
    
    private final String owner;
    private final String project;

    private final String username;
    private final String entitlement;

    public CreateAnnotatorCommand(final String owner, final String project, final String username, final String entitlement) {
        Validate.notBlank(owner, "owner must not be blank");
        Validate.notBlank(project, "project must not be blank");
        Validate.notBlank(username, "username must not be blank");
        // Validate.notBlank(entitlement, "entitlement must not be blank");
        
        this.owner = owner;
        this.project = project;
        
        this.username = username;
        this.entitlement = entitlement;
    }
}
