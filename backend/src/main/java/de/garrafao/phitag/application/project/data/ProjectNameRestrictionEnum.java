package de.garrafao.phitag.application.project.data;

public enum ProjectNameRestrictionEnum {
    INDEX("index"),
    DICTIONARY("dictionary");

    private final String name;

    ProjectNameRestrictionEnum(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static boolean contains(final String name) {
        for (ProjectNameRestrictionEnum projectNameRestriction : ProjectNameRestrictionEnum.values()) {
            if (projectNameRestriction.name.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
