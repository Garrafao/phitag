package de.garrafao.phitag.application.annotator.data.command;

import org.apache.commons.lang3.Validate;

import lombok.Getter;

@Getter
public class EditAnnotatorCommand {

    private final String owner;
    private final String project;

    private final String annotator;
    private final String entitlement;

    public EditAnnotatorCommand(final String owner, final String project, final String annotator, final String entitlement) {
        Validate.notBlank(owner, "owner must not be blank");
        Validate.notBlank(project, "project must not be blank");
        Validate.notBlank(annotator, "annotator must not be blank");
        Validate.notBlank(entitlement, "entitlement must not be blank");
        
        this.owner = owner;
        this.project = project;
        
        this.annotator = annotator;
        this.entitlement = entitlement;
    }
}
