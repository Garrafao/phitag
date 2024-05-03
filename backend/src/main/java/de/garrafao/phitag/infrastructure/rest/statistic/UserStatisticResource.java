package de.garrafao.phitag.infrastructure.rest.statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.statistics.userstatistic.UserStatisticApplicationService;
import de.garrafao.phitag.application.statistics.userstatistic.data.UserStatisticDto;

@RestController
@RequestMapping(value = "/api/v1/statistic/user")
public class UserStatisticResource {

    private final UserStatisticApplicationService userStatisticApplicationService;

    @Autowired
    public UserStatisticResource(UserStatisticApplicationService userStatisticApplicationService) {
        this.userStatisticApplicationService = userStatisticApplicationService;
    }

    /**
     * Returns the user statistic for the given user.
     * 
     * @param username the username of the user
     * @return the user statistic as a {@link UserStatisticDto}
     */
    @GetMapping()
    public UserStatisticDto getUserStatistic(
            @RequestHeader("Authorization") final String authenticationToken,
            @RequestParam(value = "user") final String username) {
        return userStatisticApplicationService.getUserStatistic(authenticationToken, username);
    }
}
