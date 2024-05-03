package de.garrafao.phitag.domain.joblisting.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class IsActiveQueryComponent implements QueryComponent {
 
    private final boolean isActive;
 
    public IsActiveQueryComponent(final boolean isActive) {
        this.isActive = isActive;
    }
    
}
