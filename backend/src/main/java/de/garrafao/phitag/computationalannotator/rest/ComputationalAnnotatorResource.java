package de.garrafao.phitag.computationalannotator.rest;

import de.garrafao.phitag.application.phase.data.TutorialHistoryDto;
import de.garrafao.phitag.computationalannotator.common.command.ComputationalAnnotatorCommand;
import de.garrafao.phitag.computationalannotator.common.command.UsePairTutorialData;
import de.garrafao.phitag.computationalannotator.common.function.GetTutorialData;
import de.garrafao.phitag.computationalannotator.lexsub.service.LexsubComputationalAnnotatorService;
import de.garrafao.phitag.computationalannotator.usepair.service.UsePairComputationalAnnotatorService;
import de.garrafao.phitag.computationalannotator.wssim.service.WSSIMComputationalAnnotatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/computationalannotator")
public class ComputationalAnnotatorResource {

    private final UsePairComputationalAnnotatorService usePairComputationalAnnotatorService;
    private final LexsubComputationalAnnotatorService lexsubComputationalAnnotatorService;

    private final WSSIMComputationalAnnotatorService wssimComputationalAnnotatorService;

    private final GetTutorialData getTutorialData;

    @Autowired
    public ComputationalAnnotatorResource(final UsePairComputationalAnnotatorService usePairComputationalAnnotatorService, LexsubComputationalAnnotatorService lexsubComputationalAnnotatorService, WSSIMComputationalAnnotatorService wssimComputationalAnnotatorService, GetTutorialData getTutorialData) {
        this.usePairComputationalAnnotatorService = usePairComputationalAnnotatorService;
        this.lexsubComputationalAnnotatorService = lexsubComputationalAnnotatorService;
        this.wssimComputationalAnnotatorService = wssimComputationalAnnotatorService;
        this.getTutorialData = getTutorialData;
    }

    @PostMapping("/use-pair-annotate")
    public void usepairChatGpt(
            @RequestHeader(value = "Authorization") final String authenticationToken,
            @RequestBody ComputationalAnnotatorCommand command) {

        this.usePairComputationalAnnotatorService.usePairChatGptAnnotation(authenticationToken, command);
    }
    @PostMapping("/lexsub-annotate")
    public void lexsubChatGpt(
            @RequestHeader(value = "Authorization") final String authenticationToken,
            @RequestBody ComputationalAnnotatorCommand command) {

        this.lexsubComputationalAnnotatorService.lexsubChatGptAnnotation(authenticationToken, command);
    }

    @PostMapping("/wssim-annotate")
    public void wssimChatGpt(
            @RequestHeader(value = "Authorization") final String authenticationToken,
            @RequestBody ComputationalAnnotatorCommand command) {

        this.wssimComputationalAnnotatorService.wssimChatGptAnnotation(authenticationToken, command);
    }

    @PostMapping("/use-pair-tutorial-annotation")
    public List<TutorialHistoryDto> usepairChatGptTutorial(
            @RequestHeader(value = "Authorization") final String authenticationToken,
            @RequestBody ComputationalAnnotatorCommand command) {

       return this.usePairComputationalAnnotatorService.usePairChatGptTutorial(authenticationToken, command);
    }
    @PostMapping("/wssim-tutorial-annotation")
    public List<TutorialHistoryDto> wssimChatGptTutorial(
            @RequestHeader(value = "Authorization") final String authenticationToken,
            @RequestBody ComputationalAnnotatorCommand command) {

        return this.wssimComputationalAnnotatorService.wssimChatGptTutorial(authenticationToken, command);
    }
    @PostMapping("/lexsum-tutorial-annotation")
    public List<TutorialHistoryDto> lexsubChatGptTutorial(
            @RequestHeader(value = "Authorization") final String authenticationToken,
            @RequestBody ComputationalAnnotatorCommand command) {

        return this.lexsubComputationalAnnotatorService.lexsubChatGptTutorial(authenticationToken, command);
    }

    @GetMapping("/usepair-tutorial-data")
    public List<UsePairTutorialData> usePairTutorialData(
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase){

    return this.getTutorialData.getTutorial(owner, project, phase);
    }
}
