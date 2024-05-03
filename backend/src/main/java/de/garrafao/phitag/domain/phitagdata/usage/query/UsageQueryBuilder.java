package de.garrafao.phitag.domain.phitagdata.usage.query;

import java.util.ArrayList;
import java.util.List;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;

public class UsageQueryBuilder {

    private final List<QueryComponent> queryComponents;

    public UsageQueryBuilder() {
        this.queryComponents = new ArrayList<>();
    }

    public UsageQueryBuilder withDataid(final String dataid) {
        if (dataid == null || dataid.isEmpty() || dataid.isBlank()) {
            return this;
        }

        this.queryComponents.add(new DataidQueryComponent(dataid));
        return this;
    }

    public UsageQueryBuilder withLemma(final String lemma) {
        if (lemma == null || lemma.isEmpty() || lemma.isBlank()) {
            return this;
        }

        this.queryComponents.add(new LemmaQueryComponent(lemma));
        return this;
    }

    public UsageQueryBuilder withOwner(final String owner) {
        if (owner == null || owner.isEmpty() || owner.isBlank()) {
            return this;
        }

        this.queryComponents.add(new OwnerQueryComponent(owner));
        return this;
    }

    public UsageQueryBuilder withProject(final String project) {
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
