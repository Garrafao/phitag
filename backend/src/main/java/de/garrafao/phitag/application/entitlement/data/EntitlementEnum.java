package de.garrafao.phitag.application.entitlement.data;

public enum EntitlementEnum {
    ENTITLEMENT_ADMIN("ENTITLEMENT_ADMIN"),
    ENTITLEMENT_USER("ENTITLEMENT_USER");

    private final String name;

    EntitlementEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
