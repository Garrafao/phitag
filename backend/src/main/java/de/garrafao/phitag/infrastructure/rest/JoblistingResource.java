package de.garrafao.phitag.infrastructure.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.joblisting.JoblistingApplicationService;
import de.garrafao.phitag.application.joblisting.data.command.AddUsersFromWaitinglistCommand;
import de.garrafao.phitag.application.joblisting.data.command.CreateJoblistingCommand;
import de.garrafao.phitag.application.joblisting.data.command.JoinJoblistingCommand;
import de.garrafao.phitag.application.joblisting.data.dto.JoblistingDto;
import de.garrafao.phitag.application.joblisting.data.dto.PersonalJoblistingDto;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.joblisting.query.JoblistingQueryBuilder;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping(value = "/api/v1/joblisting")
public class JoblistingResource {

    private final CommonService commonService;

    private final JoblistingApplicationService joblistingApplicationService;

    @Autowired
    public JoblistingResource(
            final CommonService commonService,
            final JoblistingApplicationService joblistingApplicationService) {
        this.commonService = commonService;
        this.joblistingApplicationService = joblistingApplicationService;
    }

    /**
     * Returns a list of all job listings found for the given query and optional
     * open/closed filter.
     * 
     * The listings have to be active as well as the corresponding project.
     * 
     * @param query
     *              The query to search for
     * @param open
     *              If true, only open listings are returned. If false, only closed.
     *              Null ignores this
     * @return
     *         A list of job listings
     */
    @RequestMapping(value = "/query")
    public List<JoblistingDto> query(
            @RequestParam(value = "query", required = false) final String query,
            @RequestParam(value = "open", required = false) final Boolean open) {
        final Query queryObject = new JoblistingQueryBuilder()
                .withFuzzySearch(query)
                .isOpen(open)
                .isActive(true)
                .build();
        return this.joblistingApplicationService.queryJoblistingDto(queryObject);
    }

    /**
     * Returns a list of all job listings associated with the current user and
     * fullfilling the query.
     * The user is either the owner of the listing or has admin rights for the
     * project.
     * 
     * @param authenticationToken
     *                            The fields to search for
     * 
     * @param query
     *                            The query to search for
     * @return
     *         A list of job listings
     */
    @RequestMapping(value = "/personal")
    public List<PersonalJoblistingDto> personal(@RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "query", required = false) final String query,
            @RequestParam(value = "open", required = false) final Boolean open,
            @RequestParam(value = "active", defaultValue = "true") final Boolean active) {
        final Query queryObject = new JoblistingQueryBuilder()
                .withFuzzySearch(query)
                .isOpen(open)
                .isActive(active)
                .withOwner(this.commonService.getUsernameFromAuthenticationToken(authenticationToken))
                .build();
        return this.joblistingApplicationService.queryPersonalJoblistingDto(queryObject);
    }

    /**
     * Creates a new job listing.
     * 
     * @param authenticationToken
     *                            The authentication token of the user
     * @param command
     *                            The command to create the job listing
     * @return
     *         The created job listing
     */
    @PostMapping(value = "/create")
    public void newJoblisting(@RequestHeader("Authorization") String authenticationToken,
            @RequestBody CreateJoblistingCommand command) {
        this.joblistingApplicationService.create(authenticationToken, command);
    }

    /**
     * Join the job listing waitinglist, if the listing is closed, else fasttracks
     * to user being added as annotator.
     * The joblisting has to be active as well as the corresponding project.
     * 
     * @param authenticationToken
     *                            The authentication token of the user
     * @param command
     *                            The command to add users
     * @return
     *         The updated job listing
     */
    @PostMapping(value = "/join")
    public void joinJoblisting(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody JoinJoblistingCommand command) {
        this.joblistingApplicationService.join(authenticationToken, command);
    }

    /**
     * Adds users to the job listing.
     * 
     * The user has to be the owner of the listing or have admin rights for the
     * project.
     * The joblisting has to be active as well as the corresponding project.
     * 
     * @param authenticationToken
     * @param addUsersFromWaitinglistCommand
     */
    @PostMapping(value = "/add")
    public void add(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody AddUsersFromWaitinglistCommand addUsersFromWaitinglistCommand) {
        this.joblistingApplicationService.add(authenticationToken, addUsersFromWaitinglistCommand);
    }

}
