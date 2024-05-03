package de.garrafao.phitag.domain.dictionary.entry.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class DictionaryOwnerQueryComponent implements QueryComponent {

    private final String owner;

    public DictionaryOwnerQueryComponent(String owner) {
        this.owner = owner;
    }

}
