package de.garrafao.phitag.domain.annotator.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;

public class AnnotatorQueryBuilder {

    private final List<QueryComponent> components;

    public AnnotatorQueryBuilder() {
        this.components = new ArrayList<>();
    }

    public AnnotatorQueryBuilder withFuzzySearch(final String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return this;
        }
        Arrays.stream(searchTerm.trim().split(",")).forEach(field -> this.components.add(new FuzzyQueryComponent(field.trim())));
        return this;
    }

    public AnnotatorQueryBuilder withEntitlement(final String entitlement) {
        if (entitlement == null || entitlement.isEmpty() || entitlement.isBlank()) {
            return this;
        }

        this.components.add(new EntitlementQueryComponent(entitlement));
        return this;
    }

    public AnnotatorQueryBuilder withLanguage(final String language) {
        if (language == null || language.isEmpty() || language.isBlank()) {
            return this;
        }

        this.components.add(new LanguageQueryComponent(language));
        return this;
    }

    public AnnotatorQueryBuilder withAnnotationType(final String annotationType) {
        if (annotationType == null || annotationType.isEmpty() || annotationType.isBlank()) {
            return this;
        }

        this.components.add(new AnnotationTypeQueryComponent(annotationType));
        return this;
    }

    public AnnotatorQueryBuilder withOwner(final String owner) {
        if (owner == null || owner.isEmpty() || owner.isBlank()) {
            return this;
        }

        this.components.add(new OwnerQueryComponent(owner));
        return this;
    }

    public AnnotatorQueryBuilder withProject(final String project) {
        if (project == null || project.isEmpty() || project.isBlank()) {
            return this;
        }

        this.components.add(new ProjectQueryComponent(project));
        return this;
    }

    public AnnotatorQueryBuilder withUser(final String user) {
        if (user == null || user.isEmpty() || user.isBlank()) {
            return this;
        }

        this.components.add(new UserQueryComponent(user));
        return this;
    }

    public AnnotatorQueryBuilder withIsBot(final Boolean isBot) {
        if (isBot == null) {
            return this;
        }
        this.components.add(new IsBotQueryComponent(isBot));
        return this;
    }

    public Query build() {
        return new Query(this.components);
    }
    
}
