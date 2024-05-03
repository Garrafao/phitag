package de.garrafao.phitag.domain.joblisting.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class IsOpenQueryComponent implements QueryComponent {
    
    private final boolean isOpen;
    
    public IsOpenQueryComponent(final boolean isOpen) {
        this.isOpen = isOpen;
    }
    
}
