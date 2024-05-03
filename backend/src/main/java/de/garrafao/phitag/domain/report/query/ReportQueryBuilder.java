package de.garrafao.phitag.domain.report.query;

import java.util.ArrayList;
import java.util.List;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;

public class ReportQueryBuilder {
    
    private final List<QueryComponent> queryComponents;

    public ReportQueryBuilder() {
        this.queryComponents = new ArrayList<>();
    }

    public ReportQueryBuilder withUser(final String user) {
        if (user == null || user.isEmpty() || user.isBlank()) {
            return this;
        }

        this.queryComponents.add(new UserQueryComponent(user));
        return this;
    }

    public ReportQueryBuilder withStatus(final String status) {
        if (status == null || status.isEmpty() || status.isBlank()) {
            return this;
        }

        this.queryComponents.add(new StatusQueryComponent(status));
        return this;
    }

    public Query build() {
        return new Query(this.queryComponents);
    }

}
