package de.garrafao.phitag.domain.user.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class LanguageQueryComponent implements QueryComponent {

    private final String language;

    public LanguageQueryComponent(String language) {
        this.language = language;
    }
    
}
