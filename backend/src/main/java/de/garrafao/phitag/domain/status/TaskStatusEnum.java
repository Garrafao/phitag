package de.garrafao.phitag.domain.status;

public enum TaskStatusEnum {
    
    TASK_PENDING("Pending"),
    TASK_STARTED("Started"),
    TASK_COMPLETED("Completed"),
    TASK_FAILED("Failed");

    private final String name;

    TaskStatusEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static boolean contains(String name) {
        for (TaskStatusEnum status : TaskStatusEnum.values()) {
            if (status.name.equals(name)) {
                return true;
            }
        }
        return false;
    }



}
