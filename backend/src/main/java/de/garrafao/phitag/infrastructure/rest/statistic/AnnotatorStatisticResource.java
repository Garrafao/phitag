package de.garrafao.phitag.infrastructure.rest.statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.statistics.annotatostatistic.AnnotatorStatisticApplicationService;
import de.garrafao.phitag.application.statistics.annotatostatistic.data.command.UpdateAnnotatorStatisticKrippendorffCommand;
import de.garrafao.phitag.application.statistics.annotatostatistic.data.dto.AnnotatorStatisticDto;

@RestController
@RequestMapping(value = "/api/v1/statistic/annotator")
public class AnnotatorStatisticResource {

    private final AnnotatorStatisticApplicationService annotatorStatisticApplicationService;

    @Autowired
    public AnnotatorStatisticResource(AnnotatorStatisticApplicationService annotatorStatisticApplicationService) {
        this.annotatorStatisticApplicationService = annotatorStatisticApplicationService;
    }

    /**
     * Returns the annotator statistic for the given annotator.
     * 
     * @param authenticationToken authenticationToken
     * @param annotator           annotator name
     * @param owner               owner of the project
     * @param project             project name
     * @return the annotator statistic as a {@link AnnotatorStatisticDto}
     */
    @GetMapping()
    public AnnotatorStatisticDto getAnnotatorStatistic(
        @RequestHeader("Authorization") String authenticationToken,
        @RequestParam(value = "annotator") final String annotator,
        @RequestParam(value = "owner") final String owner,
        @RequestParam(value = "project") final String project) {
        return annotatorStatisticApplicationService.getAnnotatorStatistic(authenticationToken, annotator, owner, project);
    }


    /**
     * Update Krippendorff's alpha for the given annotator.
     * 
     * @param authenticationToken the authentication token
     * @param command             the command
     */
    @PostMapping(value = "/krippendorff")
    public void updateKrippendorff(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final UpdateAnnotatorStatisticKrippendorffCommand command) {
        this.annotatorStatisticApplicationService.updateAnnotatorStatisticKrippendorff(authenticationToken, command);
    }
}
