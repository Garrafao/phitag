package de.garrafao.phitag.domain.corpuslexicon.query;

import java.util.ArrayList;
import java.util.List;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;

public class CorpusLexiconQueryBuilder {

    private final List<QueryComponent> queryComponents;

    public CorpusLexiconQueryBuilder() {
        this.queryComponents = new ArrayList<>();
    }

    public CorpusLexiconQueryBuilder withLikeToken(final String token) {
        if (token == null  || token.isBlank() || token.length() < 3) {
            return this;
        }
        queryComponents.add(new LikeTokenQueryComponent(token));
        return this;
    }

    public CorpusLexiconQueryBuilder withLemma(final String lemma) {
        if (lemma == null  || lemma.isBlank()) {
            return this;
        }
        queryComponents.add(new LemmaQueryComponent(lemma));
        return this;
    }

    public CorpusLexiconQueryBuilder withPoS(final String pos) {
        if (pos == null  || pos.isBlank()) {
            return this;
        }
        queryComponents.add(new PoSQueryComponent(pos));
        return this;
    }

    public Query build() {
        return new Query(queryComponents);
    }
    
}
