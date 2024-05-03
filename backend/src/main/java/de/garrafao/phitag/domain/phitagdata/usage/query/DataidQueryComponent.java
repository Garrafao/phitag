package de.garrafao.phitag.domain.phitagdata.usage.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class DataidQueryComponent implements QueryComponent {

    private final String dataid;

    public DataidQueryComponent(final String dataid) {
        this.dataid = dataid;
    }
    
}
