package de.garrafao.phitag.domain.project.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class VisibilityQueryComponent implements QueryComponent {
    
    private final String visibility;

    public VisibilityQueryComponent(String visibility) {
        this.visibility = visibility;
    }
    
}
