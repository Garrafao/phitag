package de.garrafao.phitag.domain.phase.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class NameQueryComponent implements QueryComponent {

    private final String name;

    public NameQueryComponent(String name) {
        this.name = name;
    }

}
