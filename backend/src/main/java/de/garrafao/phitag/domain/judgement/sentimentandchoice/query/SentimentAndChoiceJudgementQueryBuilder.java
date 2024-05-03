package de.garrafao.phitag.domain.judgement.sentimentandchoice.query;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;

import java.util.ArrayList;
import java.util.List;

public class SentimentAndChoiceJudgementQueryBuilder {

    private final List<QueryComponent> queryComponents;

    public SentimentAndChoiceJudgementQueryBuilder() {
        this.queryComponents = new ArrayList<>();
    }

    public SentimentAndChoiceJudgementQueryBuilder withUUID(final String UUID) {
        if (UUID == null || UUID.isBlank()) {
            return this;
        }
        
        this.queryComponents.add(new UUIDQueryComponent(UUID));
        return this;
    }

    public SentimentAndChoiceJudgementQueryBuilder withAnnotator(final String annotator) {
        if (annotator == null || annotator.isEmpty() || annotator.isBlank()) {
            return this;
        }

        this.queryComponents.add(new AnnotatorQueryComponent(annotator));
        return this;
    }

    public SentimentAndChoiceJudgementQueryBuilder withInstanceid(final String instanceid) {
        if (instanceid == null || instanceid.isEmpty() || instanceid.isBlank()) {
            return this;
        }

        this.queryComponents.add(new InstanceidQueryComponent(instanceid));
        return this;
    }

    public SentimentAndChoiceJudgementQueryBuilder withOwner(final String owner) {
        if (owner == null || owner.isEmpty() || owner.isBlank()) {
            return this;
        }

        this.queryComponents.add(new OwnerQueryComponent(owner));
        return this;
    }

    public SentimentAndChoiceJudgementQueryBuilder withProject(final String project) {
        if (project == null || project.isEmpty() || project.isBlank()) {
            return this;
        }

        this.queryComponents.add(new ProjectQueryComponent(project));
        return this;
    }

    public SentimentAndChoiceJudgementQueryBuilder withPhase(final String phase) {
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
