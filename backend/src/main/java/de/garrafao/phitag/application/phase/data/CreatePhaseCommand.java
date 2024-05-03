package de.garrafao.phitag.application.phase.data;

import org.apache.commons.lang3.Validate;

import lombok.Getter;

@Getter
public class CreatePhaseCommand {

    private final String name;
    private final String owner;
    private final String project;

    private final String annotationType;
    private final String sampling;

    private final boolean tutorial;
    private final String annotationAgreement;
    private final Double threshold;

    private final String description;

    private final String taskhead;

    private final Integer instancePerSample;


    public CreatePhaseCommand(
            final String name, final String owner, final String project,
            final String annotationType, final String sampling,
            final boolean tutorial, final String annotationAgreement, final Double threshold,
            final String description, final String taskhead,
            final Integer instancePerSample) {
        Validate.notBlank(name, "name must not be blank");
        Validate.notBlank(owner, "owner must not be blank");
        Validate.notBlank(project, "project must not be blank");
        Validate.notBlank(annotationType, "annotationType must not be blank");
        
        if (tutorial) {
            Validate.notBlank(annotationAgreement, "annotationAgreement must not be blank");
            this.annotationAgreement = annotationAgreement;
            this.threshold = threshold;
        } else {
            this.annotationAgreement = null;
            this.threshold = null;
        }

        this.name = name;
        this.owner = owner;
        this.project = project;

        this.annotationType = annotationType;
        this.sampling = sampling;

        this.tutorial = tutorial;

        this.description = description;
        this.taskhead = taskhead;
        this.instancePerSample = instancePerSample;
    }
}
