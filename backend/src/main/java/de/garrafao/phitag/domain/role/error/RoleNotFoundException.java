package de.garrafao.phitag.domain.role.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class RoleNotFoundException extends CustomRuntimeException {

    public RoleNotFoundException() {
        super("Role not found");
    }

    
}
