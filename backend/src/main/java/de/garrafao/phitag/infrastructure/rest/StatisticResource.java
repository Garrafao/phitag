package de.garrafao.phitag.infrastructure.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.statistics.deprecated.data.AnnotatorStatisticDto;
import de.garrafao.phitag.application.statistics.deprecated.StatisticApplicationService;

@RestController
@RequestMapping(value = "/api/deprecated/statistic/")
public class StatisticResource {

    private final StatisticApplicationService statisticApplicationService;

    @Autowired
    public StatisticResource(StatisticApplicationService statisticApplicationService) {
        this.statisticApplicationService = statisticApplicationService;
    }

    /**
     * Returns the statistics for the given project.
     * 
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project, or
     * - Project must be public
     * - If project is not active, only the owner can see the phases
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param project
     *                            The project to get the statistics for
     * @return
     *         The statistics for the given project
     */
    @GetMapping(value = "/project")
    public List<AnnotatorStatisticDto> getProjectStatistic(
            @RequestHeader("Authorization") final String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project) {
        return statisticApplicationService.getProjectStatistic(authenticationToken, owner, project);
    }

    /**
     * Returns the statistics for the given phase.
     * 
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project, or
     * - Project must be public
     * - If project is not active, only the owner can see the phases
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param phase
     *                            The phase to get the statistics for
     * @return
     *         The statistics for the given project
     */
    @GetMapping(value = "/phase")
    public List<AnnotatorStatisticDto> getPhaseStatistic(
            @RequestHeader("Authorization") final String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase) {
        return statisticApplicationService.getPhaseStatistic(authenticationToken, owner, project, phase);
    }

    /**
     * Returns the statistics for the given annotator.
     * 
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project, or
     * - Project must be public
     * - If project is not active, only the owner can see the phases
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param annotator
     *                            The annotator to get the statistics for
     * @param project
     *                            The project to get the statistics for
     * @return
     *         The statistics for the given project
     */
    @GetMapping(value = "/annotator")
    public AnnotatorStatisticDto getAnnotatorStatistic(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "annotator") final String annotator) {
        return statisticApplicationService.getAnnotatorStatistic(authenticationToken, owner, project, annotator);
    }
}
