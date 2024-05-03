package de.garrafao.phitag.domain.corpus.query;

import java.util.ArrayList;
import java.util.List;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;

public class CorpusQueryBuilder {

    private final List<QueryComponent> queryComponents;

    public CorpusQueryBuilder() {
        this.queryComponents = new ArrayList<>();
    }

    public CorpusQueryBuilder withLexiconIds(final List<String> lexiconIds) {
        if (lexiconIds == null) {
            return this;
        }
        // If the list is empty, we still add the component to the query to avoid querying the whole corpus
        queryComponents.add(new LexiconIdsQueryComponent(lexiconIds));
        return this;
    }

    public CorpusQueryBuilder betweenDate(final Integer from, final Integer to) {
        if (from == null || to == null) {
            return this;
        }
        queryComponents.add(new BetweenDateQueryComponent(from, to));
        return this;
    }
    
    public CorpusQueryBuilder withCorpusNames(final List<String> corpuses) {
        if (corpuses == null  || corpuses.isEmpty()) {
            return this;
        }
        queryComponents.add(new CorpusNameQueryComponent(corpuses));
        return this;
    }

    public Query build() {
        return new Query(queryComponents);
    }


    
}
