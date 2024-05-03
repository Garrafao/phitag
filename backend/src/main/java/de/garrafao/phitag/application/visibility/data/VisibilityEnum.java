package de.garrafao.phitag.application.visibility.data;

public enum VisibilityEnum {

    VISIBILITY_PUBLIC("VISIBILITY_PUBLIC"),
    VISIBILITY_PRIVATE("VISIBILITY_PRIVATE");

    private final String name;

    VisibilityEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static boolean contains(final String name) {
        for (VisibilityEnum visibility : VisibilityEnum.values()) {
            if (visibility.name.equals(name)) {
                return true;
            }
        }
        return false;
    }
    
}
