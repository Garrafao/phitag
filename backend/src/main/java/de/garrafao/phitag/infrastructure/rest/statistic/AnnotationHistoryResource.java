package de.garrafao.phitag.infrastructure.rest.statistic;


import de.garrafao.phitag.application.statistics.annotatorhistorytable.AnnotatorStatisticHistoryApplicationService;
import de.garrafao.phitag.application.statistics.annotatorhistorytable.data.command.AddAnnotatorStaticHistoryCommand;
import de.garrafao.phitag.application.statistics.annotatorhistorytable.data.dto.AnnotatorStatisticHistoryDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/statistics/history")
public class AnnotationHistoryResource {

    private final AnnotatorStatisticHistoryApplicationService annotatorStatisticHistoryApplicationService;

    public AnnotationHistoryResource(AnnotatorStatisticHistoryApplicationService annotatorStatisticHistoryApplicationService) {
        this.annotatorStatisticHistoryApplicationService = annotatorStatisticHistoryApplicationService;
    }

    @PostMapping("/save")
    public void save( @RequestHeader("Authorization") String authenticationToken,
                      @RequestBody final AddAnnotatorStaticHistoryCommand command) {
        this.annotatorStatisticHistoryApplicationService.save(authenticationToken, command);

    }

    @GetMapping("/getAll")
    public List<AnnotatorStatisticHistoryDto> fetchAll(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "annotatorname") final String annotatorname,
            @RequestParam(value = "ownername") final String ownername,
            @RequestParam(value = "projectname") final String projectname,
            @RequestParam(value = "phasename") final String phasename) {
       return this.annotatorStatisticHistoryApplicationService.fetchAll(authenticationToken, annotatorname,
                ownername, projectname, phasename);

    }
}
