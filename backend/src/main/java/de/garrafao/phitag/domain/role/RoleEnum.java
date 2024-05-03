package de.garrafao.phitag.domain.role;

public enum RoleEnum {
    
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER"),
    ROLE_PROLIFIC("ROLE_PROLIFIC"),

    ROLE_BOT("ROLE_BOT");

    private final String name;

    RoleEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static boolean contains(String name) {
        for (RoleEnum role : RoleEnum.values()) {
            if (role.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

}
