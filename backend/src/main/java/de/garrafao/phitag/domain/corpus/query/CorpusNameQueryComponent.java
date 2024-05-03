package de.garrafao.phitag.domain.corpus.query;

import java.util.List;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class CorpusNameQueryComponent implements QueryComponent {

    private final List<String> corpusnames;

    public CorpusNameQueryComponent(final List<String> corpusnames) {
        this.corpusnames = corpusnames;
    }

}
