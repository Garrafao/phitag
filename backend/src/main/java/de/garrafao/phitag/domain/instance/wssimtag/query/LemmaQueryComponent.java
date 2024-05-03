package de.garrafao.phitag.domain.instance.wssimtag.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class LemmaQueryComponent implements QueryComponent {

    private final String lemma;

    public LemmaQueryComponent(final String lemma) {
        this.lemma = lemma;
    }
    
}
