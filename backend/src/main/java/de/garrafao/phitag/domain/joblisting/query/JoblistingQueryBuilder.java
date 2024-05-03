package de.garrafao.phitag.domain.joblisting.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;

public class JoblistingQueryBuilder {

    private final List<QueryComponent> components;

    public JoblistingQueryBuilder() {
        this.components = new ArrayList<>();
    }

    public JoblistingQueryBuilder withFuzzySearch(final String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return this;
        }
        Arrays.stream(searchTerm.trim().split(",")).forEach(field -> this.components.add(new FuzzyQueryComponent(field.trim())));
        return this;
    }

    public JoblistingQueryBuilder isActive(final boolean isActive) {
        this.components.add(new IsActiveQueryComponent(isActive));
        return this;
    }

    public JoblistingQueryBuilder isOpen(final boolean isOpen) {
        this.components.add(new IsOpenQueryComponent(isOpen));
        return this;
    }

    public JoblistingQueryBuilder withName(final String name) {
        if (name == null || name.isEmpty() || name.isBlank()) {
            return this;
        }

        this.components.add(new NameQueryComponent(name));
        return this;
    }

    public JoblistingQueryBuilder withOwner(final String owner) {
        if (owner == null || owner.isEmpty() || owner.isBlank()) {
            return this;
        }

        this.components.add(new OwnerQueryComponent(owner));
        return this;
    }

    public JoblistingQueryBuilder withProject(final String project) {
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
