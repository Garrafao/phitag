package de.garrafao.phitag.infrastructure.rest.statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.statistics.statistictask.StatisticTaskApplicationService;
import de.garrafao.phitag.application.statistics.statistictask.data.dto.StatisticTaskDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/api/v1/statistic/task")
public class StatisticTaskResource {

    private final StatisticTaskApplicationService statisticTaskApplicationService;

    @Autowired
    public StatisticTaskResource(StatisticTaskApplicationService statisticTaskApplicationService) {
        this.statisticTaskApplicationService = statisticTaskApplicationService;
    }

    /**
     * Returns the next pendign statisic task for the given statistic bot.
     * 
     * @param authenticationToken the authentication token
     * @return the next pending statistic task as a {@link StatisticTaskDto}
     */
    @GetMapping()
    public StatisticTaskDto getNextPendingStatisticTask(
            @RequestHeader(value = "Authorization") final String authenticationToken) {
        return statisticTaskApplicationService.getNextPendingStatisticTask(authenticationToken);
    }

    /**
     * Sets the status of the given statistic task
     * 
     * @param authenticationToken the authentication token
     * @param id                  the statistic task id
     * @param status              the status
     */
    @PostMapping()
    public void setStatisticTaskStatus(
            @RequestHeader(value = "Authorization") final String authenticationToken,
            @RequestParam(value = "id") final Integer id,
            @RequestParam(value = "status") final String status) {
        statisticTaskApplicationService.setStatisticTaskStatus(authenticationToken, id, status);
    }

}
