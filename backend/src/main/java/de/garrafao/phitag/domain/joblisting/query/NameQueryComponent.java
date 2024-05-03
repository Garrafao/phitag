package de.garrafao.phitag.domain.joblisting.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class NameQueryComponent implements QueryComponent {

    private final String name;

    public NameQueryComponent(final String name) {
        this.name = name;
    }

}
