package de.garrafao.phitag.domain.dictionary.entry.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class HeadwordQueryComponent implements QueryComponent {

    private final String headword;

    public HeadwordQueryComponent(String headword) {
        this.headword = headword;
    }
    
}
