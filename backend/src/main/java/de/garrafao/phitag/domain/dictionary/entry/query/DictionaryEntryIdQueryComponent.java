package de.garrafao.phitag.domain.dictionary.entry.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class DictionaryEntryIdQueryComponent implements QueryComponent {

    private final String id;

    public DictionaryEntryIdQueryComponent(final String id) {
        this.id = id;
    }

}
