package de.garrafao.phitag.infrastructure.rest.statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.statistics.projectstatic.ProjectStatisticApplicationService;
import de.garrafao.phitag.application.statistics.projectstatic.data.ProjectStatisticDto;

@RestController
@RequestMapping(value = "/api/v1/statistic/project")
public class ProjectStatisticResource {

    private final ProjectStatisticApplicationService projectStatisticApplicationService;

    @Autowired
    public ProjectStatisticResource(ProjectStatisticApplicationService projectStatisticApplicationService) {
        this.projectStatisticApplicationService = projectStatisticApplicationService;
    }

    /**
     * Returns the project statistic for the given project.
     * 
     * @param user    the username
     * @param project the project name
     * @return the project statistic as a {@link ProjectStatisticDto}
     */
    @GetMapping()
    public ProjectStatisticDto getProjectStatistic(
        @RequestHeader("Authorization") String authenticationToken,
        @RequestParam(value = "user") final String user,
        @RequestParam(value = "project") final String project) {
        return projectStatisticApplicationService.getProjectStatistic(authenticationToken, user, project);
    }

}
