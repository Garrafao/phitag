package de.garrafao.phitag.domain.project.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;

public class ProjectQueryBuilder {

    private final List<QueryComponent> queryComponents;
    
    public ProjectQueryBuilder() {
        this.queryComponents = new ArrayList<>();
    }

    public ProjectQueryBuilder withFuzzySearch(final String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return this;
        }
        Arrays.stream(searchTerm.trim().split(",")).forEach(field -> this.queryComponents.add(new FuzzyQueryComponent(field.trim())));
        return this;
    }

    public ProjectQueryBuilder isActive(final Boolean status) {
        if (status == null) {
            return this;
        }
        this.queryComponents.add(new IsActiveQueryComponent(status));
        return this;
    }

    public ProjectQueryBuilder withLanguage(final String language) {
        if (language == null || language.isEmpty() || language.isBlank()) {
            return this;
        }
        this.queryComponents.add(new LanguageQueryComponent(language));
        return this;
    }

    public ProjectQueryBuilder withName(final String name) {
        if (name == null || name.isEmpty() || name.isBlank()) {
            return this;
        }
        this.queryComponents.add(new NameQueryComponent(name));
        return this;
    }

    public ProjectQueryBuilder withOwner(final String owner) {
        if (owner == null || owner.isEmpty() || owner.isBlank()) {
            return this;
        }
        this.queryComponents.add(new OwnerQueryComponent(owner));
        return this;
    }

    public ProjectQueryBuilder withVisibility(final String visibility) {
        if (visibility == null || visibility.isEmpty() || visibility.isBlank()) {
            return this;
        }
        this.queryComponents.add(new VisibilityQueryComponent(visibility));
        return this;
    }

    public Query build() {
        return new Query(this.queryComponents);
    }

}
