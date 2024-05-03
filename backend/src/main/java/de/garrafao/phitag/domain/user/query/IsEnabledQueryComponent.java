package de.garrafao.phitag.domain.user.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class IsEnabledQueryComponent implements QueryComponent {

    private final Boolean enabled;

    public IsEnabledQueryComponent(Boolean enabled) {
        this.enabled = enabled;
    }
    
}
