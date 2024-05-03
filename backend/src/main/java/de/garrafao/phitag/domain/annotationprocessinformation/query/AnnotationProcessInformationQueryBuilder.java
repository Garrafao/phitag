package de.garrafao.phitag.domain.annotationprocessinformation.query;

import java.util.ArrayList;
import java.util.List;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;

public class AnnotationProcessInformationQueryBuilder {

    private final List<QueryComponent> queryComponents;

    public AnnotationProcessInformationQueryBuilder() {
        this.queryComponents = new ArrayList<>();
    }

    
    public AnnotationProcessInformationQueryBuilder withAnnotator(final String annotator) {
        if (annotator == null || annotator.isBlank()) {
            return this;
        }

        this.queryComponents.add(new AnnotatorQueryComponent(annotator));
        return this;
    }

    public AnnotationProcessInformationQueryBuilder withOwner(final String owner) {
        if (owner == null || owner.isBlank()) {
            return this;
        }

        this.queryComponents.add(new OwnerQueryComponent(owner));
        return this;
    }

    public AnnotationProcessInformationQueryBuilder withProject(final String project) {
        if (project == null || project.isBlank()) {
            return this;
        }

        this.queryComponents.add(new ProjectQueryComponent(project));
        return this;
    }

    public AnnotationProcessInformationQueryBuilder withPhase(final String phase) {
        if (phase == null || phase.isBlank()) {
            return this;
        }

        this.queryComponents.add(new PhaseQueryComponent(phase));
        return this;
    }    

    public Query build() {
        return new Query(queryComponents);
    }
    
    
}
