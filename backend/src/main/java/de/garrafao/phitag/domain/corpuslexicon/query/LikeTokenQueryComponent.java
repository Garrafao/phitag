package de.garrafao.phitag.domain.corpuslexicon.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class LikeTokenQueryComponent implements QueryComponent {

    private final String token;

    public LikeTokenQueryComponent(final String value) {
        this.token = value;
    }

}
