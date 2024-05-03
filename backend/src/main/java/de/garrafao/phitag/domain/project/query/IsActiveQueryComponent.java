package de.garrafao.phitag.domain.project.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class IsActiveQueryComponent implements QueryComponent {

    private final Boolean active;

    public IsActiveQueryComponent(Boolean active) {
        this.active = active;
    }
    
}
