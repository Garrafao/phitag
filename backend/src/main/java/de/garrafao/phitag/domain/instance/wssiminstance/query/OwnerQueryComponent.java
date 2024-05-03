package de.garrafao.phitag.domain.instance.wssiminstance.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class OwnerQueryComponent implements QueryComponent {
    
    private String owner;
    
    public OwnerQueryComponent(final String owner) {
        this.owner = owner;
    }
}
