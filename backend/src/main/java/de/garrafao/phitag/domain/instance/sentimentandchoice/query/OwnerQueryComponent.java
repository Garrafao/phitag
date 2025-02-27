package de.garrafao.phitag.domain.instance.sentimentandchoice.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class OwnerQueryComponent implements QueryComponent {

    private final String owner;

    public OwnerQueryComponent(final String owner) {
        this.owner = owner;
    }
}
