package de.garrafao.phitag.domain.instance.wssiminstance.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class PhaseQueryComponent implements QueryComponent {
    
    private String phase;
    
    public PhaseQueryComponent(final String phase) {
        this.phase = phase;
    }
}
