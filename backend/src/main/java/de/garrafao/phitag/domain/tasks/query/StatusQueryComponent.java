package de.garrafao.phitag.domain.tasks.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class StatusQueryComponent implements QueryComponent {

    private final String status;

    public StatusQueryComponent(final String status) {
        this.status = status;
    }
    
}
