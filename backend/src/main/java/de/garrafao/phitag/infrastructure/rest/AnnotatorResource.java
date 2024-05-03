package de.garrafao.phitag.infrastructure.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.annotator.AnnotatorApplicationService;
import de.garrafao.phitag.application.annotator.data.command.CreateAnnotatorCommand;
import de.garrafao.phitag.application.annotator.data.command.EditAnnotatorCommand;
import de.garrafao.phitag.application.annotator.data.command.RemoveAnnotatorCommand;
import de.garrafao.phitag.application.annotator.data.dto.AnnotatorDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping(value = "/api/v1/annotator")
public class AnnotatorResource {

    private final AnnotatorApplicationService annotatorApplicationService;

    @Autowired
    public AnnotatorResource(AnnotatorApplicationService annotatorApplicationService) {
        this.annotatorApplicationService = annotatorApplicationService;
    }

    /**
     * Returns a list of all annotators found for a specific project.
     * 
     * The requesting user has to be an annotator of the project, or the visibility
     * of the project has to be public.
     * If the project is not active, only the owner can access it.
     * 
     * @param fields
     *               The fields to search for
     * @return
     *         A list of annotators
     */
    @GetMapping(value = "/by-project")
    public List<AnnotatorDto> getAnnotatorsByProject(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project) {
        return annotatorApplicationService.getAnnotatorsOfProject(authenticationToken, owner, project);
    }

    /**
     * Returns a list of all computational annotators found for a phase 
     * @param authenticationToken Authentication Token of requestring user
     * @param owner owner of the project
     * @param project project name
     * @param phase phase name
     * @return list of {@link AnnotatorDto}
     */
    @GetMapping(value = "/computational/by-phase")
    public List<AnnotatorDto> getComputationalAnnotatorsByPhase(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase) {
        return annotatorApplicationService.getComputationalAnnotatorsOfPhase(authenticationToken, owner, project, phase);
    }

    /**
     * Checks the annotator entitlement of the requesting user for the given
     * project.
     * 
     * If the project is not active, only the owner can access it and the annotator
     * entitlement is an empty string.
     * If the user is not an annotator of the project, the annotators entitlement is
     * empty, else it contains the annotator entitlement (i.e. "ENTITLEMENT_USER",
     * "ENTITLEMENT_ADMIN").
     * 
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param project
     *                            The project to check the annotator status for
     * @return
     *         The annotator status
     */
    @GetMapping(value = "/entitlement")
    public String getAnnotatorEntitlement(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project) {
        return annotatorApplicationService.getAnnotatorEntitlement(authenticationToken, owner, project);
    }

    /**
     * Add a new annotator for a specific project.
     * 
     * The requesting user has to be an annotator of the project, with ADMIN rights.
     *
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param command
     *                            The command to create the annotator
     * @return
     *         void
     */
    @PostMapping(value = "/add")
    public void create(@RequestHeader("Authorization") String authenticationToken,
            @RequestBody CreateAnnotatorCommand command) {
        this.annotatorApplicationService.add(authenticationToken, command);
    }

    /**
     * Deletes an existing annotator.
     * 
     * This function is currently not available for the public API.
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param command
     *                            The command describing the annotator
     * @return
     *         void
     */
    @PostMapping(value = "/delete")
    public void delete(@RequestHeader("Authorization") String authenticationToken,
            @RequestBody RemoveAnnotatorCommand command) {
        this.annotatorApplicationService.delete(authenticationToken, command);
    }

    /**
     * Edits an existing annotator.
     * 
     * This function is only available for the owner or an admin for the project.
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param command
     *                            The command describing the annotator
     * @return
     *         void
     */
    @PostMapping(value = "/update")
    public void update(@RequestHeader("Authorization") String authenticationToken,
            @RequestBody EditAnnotatorCommand command) {
        this.annotatorApplicationService.update(authenticationToken, command);
    }

}
