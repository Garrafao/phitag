package de.garrafao.phitag.application.user.data;

public enum UsernameRestrictionEnum {
    INDEX("index");

    private final String name;

    UsernameRestrictionEnum(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static boolean contains(final String name) {
        for (UsernameRestrictionEnum usernameRestriction : UsernameRestrictionEnum.values()) {
            if (usernameRestriction.name.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
