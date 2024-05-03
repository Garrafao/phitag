package de.garrafao.phitag.domain.dictionary.entry.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class PartOfSpeachQueryComponent implements QueryComponent {

    private final String partOfSpeach;

    public PartOfSpeachQueryComponent(String partOfSpeach) {
        this.partOfSpeach = partOfSpeach;
    }
    
}
