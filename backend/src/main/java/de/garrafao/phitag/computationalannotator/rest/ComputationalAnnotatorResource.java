package de.garrafao.phitag.computationalannotator.rest;

import de.garrafao.phitag.application.phase.data.TutorialHistoryDto;
import de.garrafao.phitag.computationalannotator.common.command.ComputationalAnnotatorCommand;
import de.garrafao.phitag.computationalannotator.common.command.UsePairTutorialData;
import de.garrafao.phitag.computationalannotator.common.function.ExportCompuationalAnnotatorParameter;
import de.garrafao.phitag.computationalannotator.common.function.GetTutorialData;
import de.garrafao.phitag.computationalannotator.sentiment.service.SentimentComputationalAnnotatorService;
import de.garrafao.phitag.computationalannotator.usepair.service.UsePairComputationalAnnotatorService;
import de.garrafao.phitag.computationalannotator.wssim.service.WSSIMComputationalAnnotatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/computationalannotator")
public class ComputationalAnnotatorResource {

    private final UsePairComputationalAnnotatorService usePairComputationalAnnotatorService;

    private final WSSIMComputationalAnnotatorService wssimComputationalAnnotatorService;

    private final SentimentComputationalAnnotatorService sentimentComputationalAnnotatorService;

    private final ExportCompuationalAnnotatorParameter exportCompuationalAnnotatorParameter;

    private final GetTutorialData getTutorialData;



    @Autowired
    public ComputationalAnnotatorResource(final UsePairComputationalAnnotatorService usePairComputationalAnnotatorService,
                                          WSSIMComputationalAnnotatorService wssimComputationalAnnotatorService, SentimentComputationalAnnotatorService sentimentComputationalAnnotatorService, ExportCompuationalAnnotatorParameter exportCompuationalAnnotatorParameter, GetTutorialData getTutorialData) {
        this.usePairComputationalAnnotatorService = usePairComputationalAnnotatorService;
        this.wssimComputationalAnnotatorService = wssimComputationalAnnotatorService;
        this.sentimentComputationalAnnotatorService = sentimentComputationalAnnotatorService;
        this.exportCompuationalAnnotatorParameter = exportCompuationalAnnotatorParameter;
        this.getTutorialData = getTutorialData;
    }
    private static final String TEXT_CSV = "text/csv";

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

    @GetMapping(value = "/export-parameter", produces = TEXT_CSV)
    public ResponseEntity<Resource> exportParameter(
            @RequestParam(value = "key") final String key,
            @RequestParam(value = "model-name") final String model,
            @RequestParam(value = "temperature") final String temperature,
            @RequestParam(value = "topP") final String topP,
            @RequestParam(value = "system") final String system,
            @RequestParam(value = "prompt") final String prompt,
            @RequestParam(value = "finalmessage") final String finalmessage) {
        InputStreamResource streamResource = this.exportCompuationalAnnotatorParameter.exportParmeter(
                key, model,temperature, topP, system, prompt, finalmessage);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=results.csv");
        // defining the custom Content-Type
        headers.set(HttpHeaders.CONTENT_TYPE, TEXT_CSV);

        return new ResponseEntity<>(
                streamResource,
                headers,
                HttpStatus.OK);
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
