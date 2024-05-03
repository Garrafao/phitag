package de.garrafao.phitag.domain.project.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class LanguageQueryComponent implements QueryComponent {

    private final String language;

    public LanguageQueryComponent(final String language) {
        this.language = language;
    }
}
