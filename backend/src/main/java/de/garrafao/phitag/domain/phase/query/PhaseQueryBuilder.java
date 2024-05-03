package de.garrafao.phitag.domain.phase.query;

import java.util.ArrayList;
import java.util.List;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;
import de.garrafao.phitag.domain.instance.usepairinstance.query.PhaseQueryComponent;

public class PhaseQueryBuilder {
    
    private final List<QueryComponent> queryComponents;

    public PhaseQueryBuilder() {
        this.queryComponents = new ArrayList<>();
    }

    public PhaseQueryBuilder withAnnotationType(final String annotationType) {
        if (annotationType == null || annotationType.isEmpty() || annotationType.isBlank()) {
            return this;
        }
        this.queryComponents.add(new AnnotationTypeQueryComponent(annotationType));
        return this;
    }

    public PhaseQueryBuilder isTutorial(final Boolean isTutorial) {
        if (isTutorial == null) {
            return this;
        }
        this.queryComponents.add(new IsTutorialQueryComponent(isTutorial));
        return this;
    }

    public PhaseQueryBuilder withName(final String phase) {
        if (phase == null || phase.isEmpty() || phase.isBlank()) {
            return this;
        }

        this.queryComponents.add(new PhaseQueryComponent(phase));
        return this;
    }    

    public PhaseQueryBuilder withOwner(final String owner) {
        if (owner == null || owner.isEmpty() || owner.isBlank()) {
            return this;
        }

        this.queryComponents.add(new OwnerQueryComponent(owner));
        return this;
    }

    public PhaseQueryBuilder withProject(final String project) {
        if (project == null || project.isEmpty() || project.isBlank()) {
            return this;
        }

        this.queryComponents.add(new ProjectQueryComponent(project));
        return this;
    }

    public Query build() {
        return new Query(this.queryComponents);
    }
    
}
