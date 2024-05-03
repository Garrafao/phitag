package de.garrafao.phitag.application.role.data;

public enum RoleEnum {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER"),
    ROLE_PROLIFIC("ROLE_PROLIFIC");

    private final String name;

    RoleEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
