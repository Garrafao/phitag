package de.garrafao.phitag.application.tasks;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.tasks.data.NextTaskDto;
import de.garrafao.phitag.application.tasks.data.TaskDto;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.domain.authentication.error.AccessDenidedException;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.project.Project;
import de.garrafao.phitag.domain.status.Status;
import de.garrafao.phitag.domain.status.TaskStatusEnum;
import de.garrafao.phitag.domain.tasks.Task;
import de.garrafao.phitag.domain.tasks.TaskRepository;
import de.garrafao.phitag.domain.tasks.query.TaskQueryBuilder;
import de.garrafao.phitag.domain.user.User;

@Service
public class TaskApplicationService {

    // Repository

    private final TaskRepository taskRepository;

    // Services

    private final CommonService commonService;

    private final ValidationService validationService;

    // Other

    @Autowired
    public TaskApplicationService(
            final TaskRepository taskRepository,
            final CommonService commonService, final ValidationService validationService) {
        this.taskRepository = taskRepository;

        this.commonService = commonService;
        this.validationService = validationService;

    }

    /**
     * Find all Tasks for a Project
     * 
     * @param authenticationToken The authentication token
     * @param owner               The owner of the project
     * @param project             The project
     */
    public List<TaskDto> findAllTasksForProject(final String authenticationToken, final String owner,
            final String project) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Project projectEntity = this.commonService.getProject(owner, project);

        this.validationService.projectAccess(requester, projectEntity);

        final Query query = new TaskQueryBuilder().withOwner(owner).withProject(project).build();
        return this.taskRepository.findByQuery(query).stream().map(TaskDto::from).collect(Collectors.toList());
    }

    /**
     * Find all Tasks for a Phase of a Project
     * 
     * @param authenticationToken The authentication token
     * @param owner               The owner of the project
     * @param project             The project
     * @param phase               The phase
     */
    public List<TaskDto> findAllTasksForPhase(final String authenticationToken, final String owner,
            final String project,
            final String phase) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Project projectEntity = this.commonService.getProject(owner, project);

        this.validationService.projectAccess(requester, projectEntity);

        final Query query = new TaskQueryBuilder().withOwner(owner).withProject(project).withPhase(phase).build();
        return this.taskRepository.findByQuery(query).stream().map(TaskDto::from).collect(Collectors.toList());
    }

    /**
     * Get next Task for a User
     * 
     * @param authenticationToken The authentication token
     * @return A {@link TaskDto} object if a task is available, null otherwise
     */
    public NextTaskDto getNextTask(final String authenticationToken) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);

        this.validationService.botAccess(requester);

        final Status status = this.commonService.getStatus(TaskStatusEnum.TASK_PENDING.name());
        // TODO: Optimize
        final Task task = this.taskRepository.findByBotAndStatus(requester, status).stream().sorted((a, b) -> {
            return a.getId().compareTo(b.getId());
        }).findFirst().orElse(null);

        if (task == null) {
            return null;
        }

        return NextTaskDto.from(task);
    }

    /**
     * Update a Tasks status
     * 
     * @param authenticationToken The authentication token
     * @param id                 The id of the task
     * @param status             The new status
     */
    @Transactional
    public void updateTaskStatus(final String authenticationToken, final Integer id, final String status) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Task task = this.taskRepository.findById(id).orElseThrow(() -> new AccessDenidedException("Task not found"));

        this.validationService.botAccess(requester);
        if (!requester.equals(task.getBot())) {
            throw new AccessDenidedException("Task not assigned to bot");
        }

        final Status newStatus = this.commonService.getStatus(status);
        task.setStatus(newStatus);
        this.taskRepository.save(task);
    }

}
