package de.garrafao.phitag.infrastructure.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.tasks.TaskApplicationService;
import de.garrafao.phitag.application.tasks.data.NextTaskDto;
import de.garrafao.phitag.application.tasks.data.TaskDto;

@RestController
@RequestMapping(value = "/api/v1/task")
public class TaskResource {

    private final TaskApplicationService taskApplicationService;

    @Autowired
    public TaskResource(TaskApplicationService taskApplicationService) {
        this.taskApplicationService = taskApplicationService;
    }

    /**
     * Find all Tasks for a Project
     * 
     * @param authenticationToken The authentication token
     * @param owner               The owner of the project
     * @param project             The project
     * @return A {@link List} of {@link TaskDto} objects
     */
    @RequestMapping(value = "/by-project")
    public List<TaskDto> byProject(
            @RequestHeader(value = "Authorization") final String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project) {

        return this.taskApplicationService.findAllTasksForProject(authenticationToken, owner, project);
    }

    /**
     * Find all Tasks for a Phase of a Project
     * 
     * @param authenticationToken The authentication token
     * @param owner               The owner of the project
     * @param project             The project
     */
    @RequestMapping(value = "/by-phase")
    public List<TaskDto> byPhase(
            @RequestHeader(value = "Authorization") final String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase", required = false) final String phase) {

        return this.taskApplicationService.findAllTasksForPhase(authenticationToken, owner, project, phase);
    }

    /**
     * Get next Task for a User
     * 
     * @param authenticationToken The authentication token
     */
    @RequestMapping(value = "/next")
    public NextTaskDto next(
            @RequestHeader(value = "Authorization") final String authenticationToken) {
        return this.taskApplicationService.getNextTask(authenticationToken);
    }

    /**
     * Update the status of a Task
     * 
     * @param authenticationToken The authentication token
     * @param id                  The id of the task
     * @param status              The new status
     */
    @PostMapping(value = "/update-status")
    public void update(
            @RequestHeader(value = "Authorization") final String authenticationToken,
            @RequestParam(value = "id") final Integer id,
            @RequestParam(value = "status") final String status) {
        this.taskApplicationService.updateTaskStatus(authenticationToken, id, status);
    }

}
