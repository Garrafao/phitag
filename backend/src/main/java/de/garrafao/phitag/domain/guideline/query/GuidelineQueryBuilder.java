package de.garrafao.phitag.domain.guideline.query;

import java.util.ArrayList;
import java.util.List;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;

public class GuidelineQueryBuilder {

    private final List<QueryComponent> components;

    public GuidelineQueryBuilder() {
        this.components = new ArrayList<>();
    }

    public GuidelineQueryBuilder withName(final String name) {
        if (name == null || name.isEmpty() || name.isBlank()) {
            return this;
        }

        this.components.add(new NameQueryComponent(name));
        return this;
    }

    public GuidelineQueryBuilder withOwner(final String owner) {
        if (owner == null || owner.isEmpty() || owner.isBlank()) {
            return this;
        }

        this.components.add(new OwnerQueryComponent(owner));
        return this;
    }

    public GuidelineQueryBuilder withProject(final String project) {
        if (project == null || project.isEmpty() || project.isBlank()) {
            return this;
        }

        this.components.add(new ProjectQueryComponent(project));
        return this;
    }

    public Query build() {
        return new Query(this.components);
    }

}
