package de.garrafao.phitag.application.tasks.data;

import de.garrafao.phitag.domain.tasks.Task;
import lombok.Getter;

@Getter
public class NextTaskDto {

    private final Integer id;

    private final String owner;
    private final String project;
    private final String phase;

    public NextTaskDto(final Integer id, final String owner, final String project, final String phase) {
        this.id = id;
        this.owner = owner;
        this.project = project;
        this.phase = phase;
    }

    public static NextTaskDto from(final Task task) {
        return new NextTaskDto(
                task.getId(),
                task.getPhase().getId().getProjectid().getOwnername(),
                task.getPhase().getId().getProjectid().getName(),
                task.getPhase().getId().getName());
    }
}
