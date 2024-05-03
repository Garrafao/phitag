package de.garrafao.phitag.domain.tutorial.lexsub.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class PhaseQueryComponent implements QueryComponent {
    
    private final String phase;

    public PhaseQueryComponent(final String phase) {
        this.phase = phase;
    }
    
}
