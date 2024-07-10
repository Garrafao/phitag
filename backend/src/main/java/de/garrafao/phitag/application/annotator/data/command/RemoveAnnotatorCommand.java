package de.garrafao.phitag.application.annotator.data.command;

import lombok.Getter;

@Getter
public class RemoveAnnotatorCommand {

    private final String username;
    private final String project;
    private final String owner;

    public RemoveAnnotatorCommand(final String username, final String project, final String owner) {
        this.username = username;
        this.project = project;
        this.owner = owner;
    }
}
