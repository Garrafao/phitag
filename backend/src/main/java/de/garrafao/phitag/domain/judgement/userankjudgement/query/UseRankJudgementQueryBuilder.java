package de.garrafao.phitag.domain.judgement.userankjudgement.query;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;
import de.garrafao.phitag.domain.judgement.common.query.*;

import java.util.ArrayList;
import java.util.List;

public class UseRankJudgementQueryBuilder {

    private final List<QueryComponent> queryComponents;

    public UseRankJudgementQueryBuilder() {
        this.queryComponents = new ArrayList<>();
    }

    public UseRankJudgementQueryBuilder withUUID(final String UUID) {
        if (UUID == null || UUID.isBlank()) {
            return this;
        }
        
        this.queryComponents.add(new UUIDQueryComponent(UUID));
        return this;
    }

    public UseRankJudgementQueryBuilder withAnnotator(final String annotator) {
        if (annotator == null || annotator.isEmpty() || annotator.isBlank()) {
            return this;
        }

        this.queryComponents.add(new AnnotatorQueryComponent(annotator));
        return this;
    }
    public UseRankJudgementQueryBuilder withAnnotatorProjectName(final String projectname) {
        if (projectname == null || projectname.isEmpty() || projectname.isBlank()) {
            return this;
        }

        this.queryComponents.add(new AnnotatorQueryComponent(projectname));
        return this;
    }

    public UseRankJudgementQueryBuilder withInstanceid(final String instanceid) {
        if (instanceid == null || instanceid.isEmpty() || instanceid.isBlank()) {
            return this;
        }

        this.queryComponents.add(new InstanceidQueryComponent(instanceid));
        return this;
    }

    public UseRankJudgementQueryBuilder withOwner(final String owner) {
        if (owner == null || owner.isEmpty() || owner.isBlank()) {
            return this;
        }

        this.queryComponents.add(new OwnerQueryComponent(owner));
        return this;
    }

    public UseRankJudgementQueryBuilder withProject(final String project) {
        if (project == null || project.isEmpty() || project.isBlank()) {
            return this;
        }

        this.queryComponents.add(new ProjectQueryComponent(project));
        return this;
    }

    public UseRankJudgementQueryBuilder withPhase(final String phase) {
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
