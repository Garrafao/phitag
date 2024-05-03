package de.garrafao.phitag.domain.annotator.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class UserQueryComponent implements QueryComponent {

    private final String user;

    public UserQueryComponent(final String user) {
        this.user = user;
    }

}
