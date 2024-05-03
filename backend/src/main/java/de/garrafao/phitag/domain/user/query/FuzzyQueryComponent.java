package de.garrafao.phitag.domain.user.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class FuzzyQueryComponent implements QueryComponent {

    private final String value;

    public FuzzyQueryComponent(final String value) {
        this.value = value;
    }
    
}
