package de.garrafao.phitag.infrastructure.rest.statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.statistics.phasestatistic.PhaseStatisticApplicationService;
import de.garrafao.phitag.application.statistics.phasestatistic.data.command.UpdatePhaseStatisticKrippendorffCommand;
import de.garrafao.phitag.application.statistics.phasestatistic.data.dto.PhaseStatisticDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/api/v1/statistic/phase")
public class PhaseStatisticResource {

    private final PhaseStatisticApplicationService phaseStatisticApplicationService;

    @Autowired
    public PhaseStatisticResource(PhaseStatisticApplicationService phaseStatisticApplicationService) {
        this.phaseStatisticApplicationService = phaseStatisticApplicationService;
    }

    /**
     * Returns the phase statistic for the given phase.
     * 
     * @param authenticationToken the authentication token
     * @param ownername           the ownername
     * @param projectname         the projectname
     * @param phasename           the phasename
     * @return the phase statistic as a {@link PhaseStatisticDto}
     */
    @GetMapping()
    public PhaseStatisticDto getPhaseStatistic(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String ownername,
            @RequestParam(value = "project") final String projectname,
            @RequestParam(value = "phase") final String phasename) {
        return this.phaseStatisticApplicationService.getPhaseStatistic(authenticationToken, ownername, projectname,
                phasename);
    }

    /**
     * Update Krippendorff's alpha for the given phase.
     * 
     * @param authenticationToken the authentication token
     * @param command             the command
     */
    @PostMapping(value = "/krippendorff")
    public void updateKrippendorff(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final UpdatePhaseStatisticKrippendorffCommand command) {
        this.phaseStatisticApplicationService.updatePhaseStatisticKrippendorff(authenticationToken, command);
    }

}
