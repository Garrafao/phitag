package de.garrafao.phitag.domain.dictionary.entry.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class DictionarynameQueryComponent implements QueryComponent {

    private final String name;

    public DictionarynameQueryComponent(String name) {
        this.name = name;
    }
    
}
