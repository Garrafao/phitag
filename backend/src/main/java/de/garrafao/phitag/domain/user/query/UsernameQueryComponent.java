package de.garrafao.phitag.domain.user.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class UsernameQueryComponent implements QueryComponent {

    private final String username;

    public UsernameQueryComponent(final String value) {
        this.username = value;
    }
    
}
