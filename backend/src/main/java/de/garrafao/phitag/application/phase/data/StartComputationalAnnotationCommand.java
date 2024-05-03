package de.garrafao.phitag.application.phase.data;

import java.util.List;

import lombok.Getter;

@Getter
public class StartComputationalAnnotationCommand {

    private final String owner;
    private final String project;
    private final String phase;

    private final List<String> annotators;

    public StartComputationalAnnotationCommand(final String owner, final String project, final String phase, final List<String> annotators) {
        this.owner = owner;
        this.project = project;
        this.phase = phase;

        this.annotators = annotators;
    }
    
}
