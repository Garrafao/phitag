package de.garrafao.phitag.domain.phase.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class IsTutorialQueryComponent implements QueryComponent {

    private final Boolean tutorial;

    public IsTutorialQueryComponent(Boolean tutorial) {
        this.tutorial = tutorial;
    }

}
