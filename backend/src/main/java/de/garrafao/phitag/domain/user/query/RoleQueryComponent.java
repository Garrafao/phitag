package de.garrafao.phitag.domain.user.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class RoleQueryComponent implements QueryComponent {

    private final String role;

    public RoleQueryComponent(String role) {
        this.role = role;
    }
    
}
