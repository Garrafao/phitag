package de.garrafao.phitag.domain.judgement.sentimentandchoice.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class UUIDQueryComponent implements QueryComponent {

    private final String UUID;

    public UUIDQueryComponent(final String UUID) {
        this.UUID = UUID;
    }

}