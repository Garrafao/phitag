package de.garrafao.phitag.domain.tasks.query;

import java.util.ArrayList;
import java.util.List;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;

public class TaskQueryBuilder {

    private final List<QueryComponent> queryComponents;

    public TaskQueryBuilder() {
        this.queryComponents = new ArrayList<>();
    }

    public TaskQueryBuilder withBotname(final String botname) {
        if (botname == null || botname.isEmpty() || botname.isBlank()) {
            return this;
        }

        this.queryComponents.add(new BotQueryComponent(botname));
        return this;
    }

    public TaskQueryBuilder withStatus(final String status) {
        if (status == null || status.isEmpty() || status.isBlank()) {
            return this;
        }

        this.queryComponents.add(new StatusQueryComponent(status));
        return this;
    }

    public TaskQueryBuilder withOwner(final String owner) {
        if (owner == null || owner.isEmpty() || owner.isBlank()) {
            return this;
        }

        this.queryComponents.add(new OwnerQueryComponent(owner));
        return this;
    }

    public TaskQueryBuilder withProject(final String project) {
        if (project == null || project.isEmpty() || project.isBlank()) {
            return this;
        }

        this.queryComponents.add(new ProjectQueryComponent(project));
        return this;
    }

    public TaskQueryBuilder withPhase(final String phase) {
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
