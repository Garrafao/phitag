package de.garrafao.phitag.application.annotator.data.command;

import lombok.Getter;

@Getter
public class RemoveAnnotatorCommand {

    private final String username;
    private final String project;
    private final String owner;
    private final String annotator;
    private final String entitlement;

    public RemoveAnnotatorCommand(final String username, final String project, final String owner, final String annotator,
                                  final String entitlement) {
        this.username = username;
        this.project = project;
        this.owner = owner;
        this.annotator = annotator;
        this.entitlement = entitlement;
    }
}
