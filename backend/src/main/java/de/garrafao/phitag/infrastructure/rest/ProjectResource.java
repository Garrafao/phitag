package de.garrafao.phitag.infrastructure.rest;

import java.util.List;

import de.garrafao.phitag.application.project.data.UpdateProjectCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.project.ProjectApplicationService;
import de.garrafao.phitag.application.project.data.CreateProjectCommand;
import de.garrafao.phitag.application.project.data.ProjectDto;
import de.garrafao.phitag.application.visibility.data.VisibilityEnum;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.entitlement.Entitlement;
import de.garrafao.phitag.domain.project.query.ProjectQueryBuilder;
import de.garrafao.phitag.domain.visibility.Visibility;

@RestController
@RequestMapping(value = "/api/v1/project")
public class ProjectResource {

    private final ProjectApplicationService projectApplicationService;

    @Autowired
    public ProjectResource(ProjectApplicationService projectApplicationService) {
        this.projectApplicationService = projectApplicationService;
    }

    /**
     * Returns a list of all projects found by the given query values.
     * 
     * These projects have to be active and their {@link Visibility} has to be
     * public.
     * 
     * @param query
     *              The fields to search for
     * @return
     *         A list of projects
     */
    @GetMapping(value = "/query")
    public List<ProjectDto> query(@RequestParam(value = "query", required = false) final String querystring) {
        Query query = new ProjectQueryBuilder()
                .withFuzzySearch(querystring)
                .isActive(true)
                .withVisibility(VisibilityEnum.VISIBILITY_PUBLIC.name())
                .build();

        return projectApplicationService.queryProjectDto(query);
    }

    /**
     * Returns a list of all projects of the given user.
     * 
     * @param authenticationToken
     * @param user
     * @return
     */
    @GetMapping(value = "/user")
    public List<ProjectDto> userProjects(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner", required = false) final String user) {
        return projectApplicationService.getPublicProjectOfUserDtos(authenticationToken, user);
    }

    /**
     * Returns a specific project by its username and project name combination.
     * 
     * The requesting user has to be the owner of the project or an annotator of the
     * project.
     * If not, the project has to be public.
     * 
     * If the project is not active, only the owner can access it.
     * 
     * @param usernameProjectname
     *                            The username and project name combination
     * @return
     *         The project
     *
     */
    @GetMapping(value = "/find")
    public ProjectDto find(
            @RequestHeader("Authorization") final String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project) {
        return projectApplicationService.findProjectOfUserAsDto(authenticationToken, owner, project);
    }

    /**
     * Get all projects of the requesting user.
     * 
     * The user is the owner of the project.
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param query
     *                            The values to search for
     * @return
     */
    @GetMapping(value = "/personal")
    public List<ProjectDto> personalProjects(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "query", required = false) final String query) {
        return projectApplicationService.getPersonalProjectDtos(authenticationToken, query);
    }

    /**
     * Get all projects of the requesting user where the user is the annotator.
     * 
     * The user is an annotator of the project.
     * If the project is not active, only the owner can access it.
     * 
     * @param authenticationToken
     * @param command
     */
    @GetMapping(value = "/annotator")
    public List<ProjectDto> annotatorProjects(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "query", required = false) final String query) {
        return projectApplicationService.getAnnotatorProjectDtos(authenticationToken, query);
    }

    /**
     * Creates a new project.
     * 
     * The owner will automatically be determined by the authentication token and
     * also added as an annotator with admin {@link Entitlement}.
     * 
     * @param authenticationToken
     *                             The authentication token of the requesting user
     * @param createProjectCommand
     *                             The command to create the project
     * @return
     *         The created project
     */
    @PostMapping(value = "/create")
    public void create(@RequestHeader("Authorization") String authenticationToken,
            @RequestBody CreateProjectCommand command) {
        projectApplicationService.create(authenticationToken, command);
    }

    /**
     * update project.
     *

     */
    @PostMapping(value = "/update")
    public void update(@RequestHeader("Authorization") String authenticationToken,
                       @RequestBody UpdateProjectCommand command,
                       @RequestParam(value = "owner") final String owner,
                       @RequestParam(value = "project") final String project) {
        projectApplicationService.update(authenticationToken, project, owner, command);
    }


    /**
     * Deletes a project.
     * 
     * The requesting user has to be the owner of the project.
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param project
     *                            The project to delete
     */
    @PostMapping(value = "/delete")
    public void delete(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "project") final String project) {
        projectApplicationService.delete(authenticationToken, project);
    }

}
