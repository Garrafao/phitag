package de.garrafao.phitag.application.phase.data;

public enum PhaseNameRestrictionEnum {
    INDEX("index"),
    ANNOTATOR("annotator"),
    DATA("data"),
    STATISTIC("statistic"),
    TASK("task");

    private final String name;

    PhaseNameRestrictionEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static boolean contains(String name) {
        for (PhaseNameRestrictionEnum phaseNameRestriction : PhaseNameRestrictionEnum.values()) {
            if (phaseNameRestriction.name.equals(name)) {
                return true;
            }
        }
        return false;
    }
    
}
