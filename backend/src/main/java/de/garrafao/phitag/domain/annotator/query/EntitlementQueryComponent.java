package de.garrafao.phitag.domain.annotator.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class EntitlementQueryComponent implements QueryComponent {

    private final String entitlement;

    public EntitlementQueryComponent(final String entitlement) {
        this.entitlement = entitlement;
    }

}
