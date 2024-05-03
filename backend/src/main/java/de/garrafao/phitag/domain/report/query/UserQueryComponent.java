package de.garrafao.phitag.domain.report.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class UserQueryComponent implements QueryComponent {

    private final String username;

    public UserQueryComponent(final String username) {
        this.username = username;
    }
    
}
