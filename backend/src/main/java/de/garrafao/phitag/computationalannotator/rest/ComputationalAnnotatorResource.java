package de.garrafao.phitag.computationalannotator.rest;

import de.garrafao.phitag.application.phase.data.TutorialHistoryDto;
import de.garrafao.phitag.computationalannotator.common.command.ComputationalAnnotatorCommand;
import de.garrafao.phitag.computationalannotator.common.command.UsePairTutorialData;
import de.garrafao.phitag.computationalannotator.common.function.GetTutorialData;
import de.garrafao.phitag.computationalannotator.sentiment.service.SentimentComputationalAnnotatorService;
import de.garrafao.phitag.computationalannotator.usepair.service.UsePairComputationalAnnotatorService;
import de.garrafao.phitag.computationalannotator.wssim.service.WSSIMComputationalAnnotatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/computationalannotator")
public class ComputationalAnnotatorResource {

    private final UsePairComputationalAnnotatorService usePairComputationalAnnotatorService;

    private final WSSIMComputationalAnnotatorService wssimComputationalAnnotatorService;

    private final SentimentComputationalAnnotatorService sentimentComputationalAnnotatorService;
    private final GetTutorialData getTutorialData;

    @Autowired
    public ComputationalAnnotatorResource(final UsePairComputationalAnnotatorService usePairComputationalAnnotatorService,
                                          WSSIMComputationalAnnotatorService wssimComputationalAnnotatorService, SentimentComputationalAnnotatorService sentimentComputationalAnnotatorService, GetTutorialData getTutorialData) {
        this.usePairComputationalAnnotatorService = usePairComputationalAnnotatorService;
        this.wssimComputationalAnnotatorService = wssimComputationalAnnotatorService;
        this.sentimentComputationalAnnotatorService = sentimentComputationalAnnotatorService;
        this.getTutorialData = getTutorialData;
    }

    @PostMapping("/use-pair-annotate")
    public void usepairChatGpt(
            @RequestHeader(value = "Authorization") final String authenticationToken,
            @RequestBody ComputationalAnnotatorCommand command) {

        this.usePairComputationalAnnotatorService.usePairChatGptAnnotation(authenticationToken, command);
    }

    @PostMapping("/sentiment-annotate")
    public void sentimentChatGpt(
            @RequestHeader(value = "Authorization") final String authenticationToken,
            @RequestBody ComputationalAnnotatorCommand command) {

        this.sentimentComputationalAnnotatorService.sentimentGptAnnotation(authenticationToken, command);
    }

    @PostMapping("/tiny-annotate")
    public void tinyLLM(
            @RequestHeader(value = "Authorization") final String authenticationToken,
            @RequestBody ComputationalAnnotatorCommand command) {

        this.sentimentComputationalAnnotatorService.tinyLLMAnnotate(authenticationToken, command);
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

    @GetMapping("/usepair-tutorial-data")
    public List<UsePairTutorialData> usePairTutorialData(
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase){

    return this.getTutorialData.getTutorial(owner, project, phase);
    }
}
