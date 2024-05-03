package de.garrafao.phitag.domain.instance.wssimtag.query;

import java.util.ArrayList;
import java.util.List;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;

public class WSSIMTagQueryBuilder {

    private final List<QueryComponent> queryComponents;

    public WSSIMTagQueryBuilder() {
        this.queryComponents = new ArrayList<>();
    }

    public WSSIMTagQueryBuilder withTagid(final String tagid) {
        if (tagid == null || tagid.isEmpty() || tagid.isBlank()) {
            return this;
        }

        this.queryComponents.add(new TagidQueryComponent(tagid));
        return this;
    }

    public WSSIMTagQueryBuilder withLemma(final String lemma) {
        if (lemma == null || lemma.isEmpty() || lemma.isBlank()) {
            return this;
        }

        this.queryComponents.add(new LemmaQueryComponent(lemma));
        return this;
    }

    public WSSIMTagQueryBuilder withOwner(final String owner) {
        if (owner == null || owner.isEmpty() || owner.isBlank()) {
            return this;
        }

        this.queryComponents.add(new OwnerQueryComponent(owner));
        return this;
    }

    public WSSIMTagQueryBuilder withProject(final String project) {
        if (project == null || project.isEmpty() || project.isBlank()) {
            return this;
        }

        this.queryComponents.add(new ProjectQueryComponent(project));
        return this;
    }

    public WSSIMTagQueryBuilder withPhase(final String phase) {
        if (phase == null || phase.isEmpty() || phase.isBlank()) {
            return this;
        }

        this.queryComponents.add(new PhaseQueryComponent(phase));
        return this;
    }

    public Query build() {
        return new Query(queryComponents);
    }

}
