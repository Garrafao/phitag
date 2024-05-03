package de.garrafao.phitag.domain.corpus.query;

import java.util.List;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class LexiconIdsQueryComponent implements QueryComponent {

    private final List<String> lexiconIds;

    public LexiconIdsQueryComponent(final List<String> lexiconIds) {
        this.lexiconIds = lexiconIds;
    }
    
}
