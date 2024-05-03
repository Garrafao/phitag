package de.garrafao.phitag.domain.user.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class EmailQueryComponent implements QueryComponent {

    private final String email;

    public EmailQueryComponent(String email) {
        this.email = email;
    }
}
