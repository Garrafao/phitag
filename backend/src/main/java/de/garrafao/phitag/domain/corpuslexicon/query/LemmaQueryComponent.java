package de.garrafao.phitag.domain.corpuslexicon.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class LemmaQueryComponent implements QueryComponent {

    private final String lemma;

    public LemmaQueryComponent(final String value) {
        this.lemma = value;
    }

}
