package de.garrafao.phitag.domain.instance.sentimentandchoice.query;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;

import java.util.ArrayList;
import java.util.List;

public class SentimentAndChoiceInstanceQueryBuilder {

    private final List<QueryComponent> queryComponents;

    public SentimentAndChoiceInstanceQueryBuilder() {
        this.queryComponents = new ArrayList<>();
    }

    public SentimentAndChoiceInstanceQueryBuilder withInstanceid(final String instanceid) {
        if (instanceid == null || instanceid.isEmpty() || instanceid.isBlank()) {
            return this;
        }

        this.queryComponents.add(new InstanceidQueryComponent(instanceid));
        return this;
    }

    public SentimentAndChoiceInstanceQueryBuilder withOwner(final String owner) {
        if (owner == null || owner.isEmpty() || owner.isBlank()) {
            return this;
        }

        this.queryComponents.add(new OwnerQueryComponent(owner));
        return this;
    }

    public SentimentAndChoiceInstanceQueryBuilder withProject(final String project) {
        if (project == null || project.isEmpty() || project.isBlank()) {
            return this;
        }

        this.queryComponents.add(new ProjectQueryComponent(project));
        return this;
    }

    public SentimentAndChoiceInstanceQueryBuilder withPhase(final String phase) {
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
