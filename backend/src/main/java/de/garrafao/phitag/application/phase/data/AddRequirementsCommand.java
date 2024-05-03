package de.garrafao.phitag.application.phase.data;

import java.util.List;

import org.apache.commons.lang3.Validate;

import lombok.Getter;

@Getter
public class AddRequirementsCommand {

    private final String owner;
    private final String project;
    private final String phase;

    private final List<String> tutorials;

    public AddRequirementsCommand(final String owner, final String project, final String phase,
            final List<String> tutorials) {
        Validate.notBlank(owner, "owner must not be blank");
        Validate.notBlank(project, "project must not be blank");
        Validate.notBlank(phase, "name must not be blank");
        // Validate.notEmpty(tutorials, "tutorials must not be empty");

        this.owner = owner;
        this.project = project;
        this.phase = phase;

        this.tutorials = tutorials;
    }
}
