package de.garrafao.phitag.application.tasks.data;

import de.garrafao.phitag.application.phase.data.PhaseDto;
import de.garrafao.phitag.application.status.data.StatusDto;
import de.garrafao.phitag.domain.tasks.Task;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class TaskDto {

    private final String botname;
    private final String taskowner;

    private final PhaseDto phase;
    private final StatusDto status;

    private TaskDto(final String botname, final String taskowner, final PhaseDto phase, final StatusDto status) {
        this.botname = botname;
        this.taskowner = taskowner;
        this.phase = phase;
        this.status = status;
    }

    public static TaskDto from(@NonNull final Task task) {
        return new TaskDto(
                task.getBot().getUsername(),
                task.getTaskowner().getUsername(),
                PhaseDto.from(task.getPhase()),
                StatusDto.from(task.getStatus()));
    }
}
