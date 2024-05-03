package de.garrafao.phitag.infrastructure.rest;

import java.util.List;

import de.garrafao.phitag.application.phase.data.*;
import de.garrafao.phitag.domain.phase.Phase;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.phase.PhaseApplicationService;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.phase.query.PhaseQueryBuilder;

import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/v1/phase")
public class PhaseResource {

    private final PhaseApplicationService phaseApplicationService;

    @Autowired
    public PhaseResource(PhaseApplicationService phaseApplicationService) {
        this.phaseApplicationService = phaseApplicationService;
    }

    /**
     * Get all phases as a list.
     * 
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project, or
     * - Project must be public
     * - If project is not active, only the owner can see the phases
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param projectname
     *                            The name of the project
     * @return
     *         A list of phases
     */
    @GetMapping(value = "/by-project")
    public List<PhaseDto> byProject(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "annotation-type", required = false) final String annotationType,
            @RequestParam(value = "tutorial", required = false) final Boolean tutorial) {
        final Query query = new PhaseQueryBuilder()
                .withOwner(owner)
                .withProject(project)
                .withAnnotationType(annotationType)
                .isTutorial(tutorial)
                .build();
        return this.phaseApplicationService.queryPhaseDtos(authenticationToken, owner, project, query);

    }

    /**
     * Resturns a specific phase of a project.
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param owner
     *                            The owner of the project
     * @param project
     *                            The name of the project
     * @param phase
     *                            The name of the phase
     * @return The phase
     */
    @GetMapping(value = "/phase")
    public PhaseDto getPhase(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase) {
        return phaseApplicationService.getPhaseDto(authenticationToken, owner, project, phase);
    }

    /**
     * Checks if requesting user has access to annotate phase.
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param owner
     *                            The owner of the project
     * @param project
     *                            The name of the project
     * @param phase
     *                            The name of the phase
     * @return
     *         True if user has access, false otherwise
     */
    @GetMapping(value = "/has-access")
    public boolean hasAccess(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase) {
        return phaseApplicationService.hasAccessToAnnotate(authenticationToken, owner, project, phase);

    }

    /**
     * Get tutorial measurement history.
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param owner
     *                            The owner of the project
     * @param project
     *                            The name of the project
     * @param phase
     *                            The name of the phase
     * @return
     *         A list of tutorial measurement history
     */

    @GetMapping(value = "/tutorial/measurement-history")
    public List<TutorialHistoryDto> tutorialMeasurementHistory(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase) {
        return phaseApplicationService.getTutorialMeasureHistory(authenticationToken, owner, project, phase);

    }

    /**
     * set code in a phase.
     * 
     * The requesting user must fulfill the following conditions:
     * - Be the owner of the project or an admin
     * - Project has to be active
     *
     * 
     * @param authenticationToken
     * @param project
     * @param owner
     * @param phase
     *
     * @param  commnad
     * @return
     */
    @PostMapping(value = "/create/code")
    public void setCode(@RequestHeader("Authorization") String authenticationToken,
                           @RequestParam(value = "owner") final String owner,
                           @RequestParam(value = "project") final String project,
                           @RequestParam(value = "phase") final String phase,
                           @RequestBody    final String code) {
        this.phaseApplicationService.createCode(authenticationToken, owner, project, phase ,code);
    }

    /**
     * Create a new phase.
     *
     * The requesting user must fulfill the following conditions:
     * - Be the owner of the project or an admin
     * - Project has to be active
     *
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param command
     *                            The command to create a new phase
     * @return
     */
    @PostMapping(value = "/create")
    public void create(@RequestHeader("Authorization") String authenticationToken,
                       @RequestBody CreatePhaseCommand command) {
        this.phaseApplicationService.create(authenticationToken, command);
    }

    /**
     * Closes a phase.
     * 
     * @param authenticationToken
     * @param owner
     * @param project
     * @param phase
     */
    @PostMapping(value = "/close")
    public void updatePhaseStatus(@RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase) {
        this.phaseApplicationService.updatePhaseStatus(authenticationToken, owner, project, phase);
    }

    /**
     * Add requirements to a phase.
     * 
     * The requesting user must fulfill the following conditions:
     * - Be the owner of the project or an admin
     * - Project has to be active
     * - Phase can not be a tutorial
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param command
     *                            The command to add requirements to a phase
     */
    @PostMapping(value = "/delete-requirements")
    public void deleteRequirements(@RequestHeader("Authorization") String authenticationToken,
                                   @RequestParam(value = "owner") final String owner,
                                   @RequestParam(value = "project") final String project,
                                   @RequestParam(value = "phase") final String phase,
                                   @RequestParam(value = "requirements") final String requirements)  {
        this.phaseApplicationService.deleteRequirements(authenticationToken, owner, project, phase, requirements);
    }
    /**
     * Add requirements to a phase.
     *
     * The requesting user must fulfill the following conditions:
     * - Be the owner of the project or an admin
     * - Project has to be active
     * - Phase can not be a tutorial
     *
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param command
     *                            The command to add requirements to a phase
     */
    @PostMapping(value = "/add-requirements")
    public void addRequirements(@RequestHeader("Authorization") String authenticationToken,
                                @RequestBody AddRequirementsCommand command) {
        this.phaseApplicationService.addRequirements(authenticationToken, command);
    }

    /**
     * Start computational annotation for a specific project.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param command             The command to start computational annotation
     */
    @PostMapping(value = "/start-computational-annotation")
    public void startComputationalAnnotation(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final StartComputationalAnnotationCommand command) {
        phaseApplicationService.startComputationalAnnotation(authenticationToken, command);
    }

    /**
     * Delete a phase
     *
     * The requesting user has to be the owner of the project.
     *
     * @param authenticationToken   The authentication token of the requesting user
     *
     * @param owner Owner
     * @param project project
     *  @param phaseName  phaseName
     *
     */
    @PostMapping(value = "/delete")
    public void delete(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase) {
        phaseApplicationService.deletePhase(authenticationToken, owner, project, phase);
    }

}
