package de.garrafao.phitag.domain.corpuslexicon.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class PoSQueryComponent implements QueryComponent {

    private final String pos;

    public PoSQueryComponent(final String value) {
        this.pos = value;
    }
    
}
