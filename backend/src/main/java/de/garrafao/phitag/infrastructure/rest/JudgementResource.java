package de.garrafao.phitag.infrastructure.rest;

import de.garrafao.phitag.application.judgement.JudgementApplicationService;
import de.garrafao.phitag.application.judgement.data.IAddJudgementCommand;
import de.garrafao.phitag.application.judgement.data.IJudgementDto;
import de.garrafao.phitag.application.judgement.data.PagedJudgementDto;
import de.garrafao.phitag.application.judgement.lexsubjudgement.data.AddLexSubJudgementCommand;
import de.garrafao.phitag.application.judgement.lexsubjudgement.data.DeleteLexSubJudgementCommand;
import de.garrafao.phitag.application.judgement.lexsubjudgement.data.EditLexSubJudgementCommand;
import de.garrafao.phitag.application.judgement.sentimentandchoice.data.AddSentimentAndChoiceJudgementCommand;
import de.garrafao.phitag.application.judgement.sentimentandchoice.data.DeleteSentimentAndChoiceJudgementCommand;
import de.garrafao.phitag.application.judgement.sentimentandchoice.data.EditSentimentAndChoiceJudgementCommand;
import de.garrafao.phitag.application.judgement.usepairjudgement.data.AddUsePairJudgementCommand;
import de.garrafao.phitag.application.judgement.usepairjudgement.data.DeleteUsePairJudgementCommand;
import de.garrafao.phitag.application.judgement.usepairjudgement.data.EditUsePairJudgementCommand;
import de.garrafao.phitag.application.judgement.userankjudgement.data.AddUseRankJudgementCommand;
import de.garrafao.phitag.application.judgement.userankjudgement.data.DeleteUseRankJudgementCommand;
import de.garrafao.phitag.application.judgement.userankjudgement.data.EditUseRankJudgementCommand;
import de.garrafao.phitag.application.judgement.userankpairjudgement.data.AddUseRankPairJudgementCommand;
import de.garrafao.phitag.application.judgement.userankpairjudgement.data.DeleteUseRankPairJudgementCommand;
import de.garrafao.phitag.application.judgement.userankpairjudgement.data.EditUseRankPairJudgementCommand;
import de.garrafao.phitag.application.judgement.userankrelativejudgement.data.AddUseRankRelativeJudgementCommand;
import de.garrafao.phitag.application.judgement.userankrelativejudgement.data.DeleteUseRankRelativeJudgementCommand;
import de.garrafao.phitag.application.judgement.userankrelativejudgement.data.EditUseRankRelativeJudgementCommand;
import de.garrafao.phitag.application.judgement.wssimjudgement.data.AddWSSIMJudgementCommand;
import de.garrafao.phitag.application.judgement.wssimjudgement.data.DeleteWSSIMJudgementCommand;
import de.garrafao.phitag.application.judgement.wssimjudgement.data.EditWSSIMJudgementCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/judgement")
public class JudgementResource {

    private static final String TEXT_CSV = "text/csv";
    private final JudgementApplicationService judgementApplicationService;


    @Autowired
    public JudgementResource(JudgementApplicationService judgementApplicationService) {
        this.judgementApplicationService = judgementApplicationService;
    }

    /**
     * Get the judgement dto of a specific phase.
     * Underlying DTO depends on the annotation type of the phase and should be
     * resolved by the client.
     * 
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project, or
     * - Project must be public
     * - If project is not active, only the owner can see the phases
     * - If the phase is a tutorial, only the owner and admins can see the phase +
     * data
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param owner
     *                            The owner of the project
     * @param project
     *                            The name of the project
     * @param phase
     *                            The name of the phase
     * @return
     *         Phase data
     */
    @GetMapping()
    public List<IJudgementDto> getJudgementDtos(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase) {
        return this.judgementApplicationService.getJudgementDto(authenticationToken, owner, project, phase);
    }

    /**
     * Get the judgement dto of a specific phase as a paged result.
     * 
     * @param authenticationToken
     * @param owner
     * @param project
     * @param phase
     * @param page
     * @return
     */
    @GetMapping(value = "/paged")
    public PagedJudgementDto getPagedJudgementDtos(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase,
            @RequestParam(value = "page") final int page) {
        return this.judgementApplicationService.getPagedJudgementDto(authenticationToken, owner, project, phase, page,
                50, null);
    }

    /**
     * Get personal history of a specific phase.
     * Underlying DTO depends on the annotation type of the phase and should be
     * resolved by the client
     * 
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project, and
     * - Phase must not be a tutorial
     * - If project is not active, only the owner can see the history
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param owner
     *                            The owner of the project
     * @param project
     *                            The name of the project
     * @param phase
     *                            The name of the phase
     * @return
     *         Phase data
     */
    @GetMapping(value = "/history/personal")
    public List<IJudgementDto> getHistory(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase) {
        return this.judgementApplicationService.getHistory(authenticationToken, owner, project, phase);
    }

    /**
     * Get personal history of a specific phase as a paged result.
     * 
     * @param authenticationToken
     * @param owner
     * @param project
     * @param phase
     * @param page
     * @return
     */
    @GetMapping(value = "/history/personal/paged")
    public PagedJudgementDto getPagedHistory(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase,
            @RequestParam(value = "page") final int page) {
        return this.judgementApplicationService.getPagedHistory(authenticationToken, owner, project, phase, page, 50,
                null);
    }

    /**
     * Export result data of a specific phase.
     * 
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project with admin rights, and
     * - If project is not active, only the owner can see the phases
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param owner
     *                            The owner of the project
     * @param project
     *                            The name of the project
     * @param phase
     *                            The name of the phase
     * @return
     *         Data as CSV
     */
    @GetMapping(value = "/export", produces = TEXT_CSV)
    public ResponseEntity<Resource> exportJudgement(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase) {
        InputStreamResource streamResource = this.judgementApplicationService.exportJudgement(authenticationToken,
                owner, project, phase);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=results.csv");
        // defining the custom Content-Type
        headers.set(HttpHeaders.CONTENT_TYPE, TEXT_CSV);

        return new ResponseEntity<>(
                streamResource,
                headers,
                HttpStatus.OK);
    }

    /**
     * Export result data of a specific phase.
     *
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project with admin rights, and
     * - If project is not active, only the owner can see the phases
     *
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param owner
     *                            The owner of the project
     * @param project
     *                            The name of the project
     * @param phase
     *                            The name of the phase
     * @return
     *         Data as CSV
     */
    @GetMapping(value = "/export/tutorial", produces = TEXT_CSV)
    public ResponseEntity<Resource> exportTutorialJudgement(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase) {
        InputStreamResource streamResource = this.judgementApplicationService.exportTutorialJudgement(authenticationToken,
                owner, project, phase);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=results.csv");
        // defining the custom Content-Type
        headers.set(HttpHeaders.CONTENT_TYPE, TEXT_CSV);

        return new ResponseEntity<>(
                streamResource,
                headers,
                HttpStatus.OK);
    }

    /**
     * Add judgement data to a phase.
     * 
     * The requesting user must fulfill the following conditions:
     * - Be the owner of the project or an admin
     * - Project has to be active
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param owner
     *                            The owner of the project
     * @param project
     *                            The name of the project
     * @param phase
     *                            The name of the phase
     * @param file
     *                            The resultdata to add
     */
    @PostMapping()
    public void addJudgements(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase,
            @RequestParam(value = "file") final MultipartFile file) {
        this.judgementApplicationService.addJudgements(authenticationToken, owner, project, phase, file);
    }

    /**
     * Edit UsePairJudgement data
     * 
     * @param authenticationToken
     * @param command
     */
    @PostMapping(value = "/edit/usepair")
    public void editUsePairJudgement(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final EditUsePairJudgementCommand command) {
        this.judgementApplicationService.edit(authenticationToken, command);
    }

    /**
     * Edit UseRankJudgement data
     *
     * @param authenticationToken
     * @param command
     */
    @PostMapping(value = "/edit/userank")
    public void editUseRankJudgement(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final EditUseRankJudgementCommand command) {
        this.judgementApplicationService.edit(authenticationToken, command);
    }

    /**
     * Edit UseRankJudgement data
     *
     * @param authenticationToken
     * @param command
     */
    @PostMapping(value = "/edit/userankrelative")
    public void editUseRankRelativeJudgement(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final EditUseRankRelativeJudgementCommand command) {
        this.judgementApplicationService.edit(authenticationToken, command);
    }
    /**
     * Edit UseRankJudgement data
     *
     * @param authenticationToken
     * @param command
     */
    @PostMapping(value = "/edit/userankpair")
    public void editUseRankPairJudgement(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final EditUseRankPairJudgementCommand command) {
        this.judgementApplicationService.edit(authenticationToken, command);
    }

    /**
     * Edit WSSIMJudgement data
     * 
     * @param authenticationToken
     * @param command
     */
    @PostMapping(value = "/edit/wssim")
    public void editWSSIMJudgement(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final EditWSSIMJudgementCommand command) {
        this.judgementApplicationService.edit(authenticationToken, command);
    }

    /**
     * Edit LexSubJudgement data
     * 
     * @param authenticationToken
     * @param command
     */
    @PostMapping(value = "/edit/lexsub")
    public void editLexSubJudgement(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final EditLexSubJudgementCommand command) {
        this.judgementApplicationService.edit(authenticationToken, command);
    }

    /**
     * Edit SentimentJudgement data
     *
     * @param authenticationToken
     * @param command
     */
    @PostMapping(value = "/edit/sentiment")
    public void editSentimentJudgement(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final EditSentimentAndChoiceJudgementCommand command) {
        this.judgementApplicationService.edit(authenticationToken, command);
    }


    /**
     * Delete UsePairJudgement data
     * 
     * @param authenticationToken
     * @param command
     */
    @PostMapping(value = "/delete/usepair")
    public void deleteUsePairJudgement(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final DeleteUsePairJudgementCommand command) {
        this.judgementApplicationService.delete(authenticationToken, command);
    }
    /**
     * Delete UseRankJudgement data
     *
     * @param authenticationToken
     * @param command
     */
    @PostMapping(value = "/delete/userank")
    public void deleteUseRankJudgement(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final DeleteUseRankJudgementCommand command) {
        this.judgementApplicationService.delete(authenticationToken, command);
    }
    /**
     * Delete UseRankJudgement data
     *
     * @param authenticationToken
     * @param command
     */
    @PostMapping(value = "/delete/userankpair")
    public void deleteUseRankPairJudgement(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final DeleteUseRankPairJudgementCommand command) {
        this.judgementApplicationService.delete(authenticationToken, command);
    }

    /**
     * Delete UseRankRelativeJudgement data
     *
     * @param authenticationToken
     * @param command
     */
    @PostMapping(value = "/delete/userankrelative")
    public void deleteUseRankRelativeJudgement(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final DeleteUseRankRelativeJudgementCommand command) {
        this.judgementApplicationService.delete(authenticationToken, command);
    }



    /**
     * Delete WSSIMJudgement data
     * 
     * @param authenticationToken
     * @param command
     */
    @PostMapping(value = "/delete/wssim")
    public void deleteWSSIMJudgement(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final DeleteWSSIMJudgementCommand command) {
        this.judgementApplicationService.delete(authenticationToken, command);
    }

    /**
     * Delete LexSubJudgement data
     * 
     * @param authenticationToken
     * @param command
     */
    @PostMapping(value = "/delete/lexsub")
    public void deleteLexSubJudgement(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final DeleteLexSubJudgementCommand command) {
        this.judgementApplicationService.delete(authenticationToken, command);
    }

    /**
     * Delete SentimentJudgement data
     *
     * @param authenticationToken
     * @param command
     */
    @PostMapping(value = "/delete/sentiement")
    public void deleteSentimentJudgement(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final DeleteSentimentAndChoiceJudgementCommand command) {
        this.judgementApplicationService.delete(authenticationToken, command);
    }


    /**
     * Annote a specific instance of a phase.
     * 
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project, and
     * - Project must be active
     * - Phase must not be a tutorial
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param command
     */
    @PostMapping(value = "/annotate/usepair")
    public void annotateUsepair(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final AddUsePairJudgementCommand command) {
        this.judgementApplicationService.annotate(authenticationToken, command);
    }

    /**
     * Annote a specific instance of a phase.
     *
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project, and
     * - Project must be active
     * - Phase must not be a tutorial
     *
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param command
     */
    @PostMapping(value = "/annotate/userank")
    public void annotateUserank(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final AddUseRankJudgementCommand command) {
        this.judgementApplicationService.annotate(authenticationToken, command);
    }


    /**
     * Annote a specific instance of a phase.
     *
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project, and
     * - Project must be active
     * - Phase must not be a tutorial
     *
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param command
     */
    @PostMapping(value = "/annotate/userankrelative")
    public void annotateUserankrelative(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final AddUseRankRelativeJudgementCommand command) {
        this.judgementApplicationService.annotate(authenticationToken, command);
    }

    /**
     * Annote a specific instance of a phase.
     *
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project, and
     * - Project must be active
     * - Phase must not be a tutorial
     *
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param command
     */
    @PostMapping(value = "/annotate/userankpair")
    public void annotateUserankpair(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final AddUseRankPairJudgementCommand command) {
        this.judgementApplicationService.annotate(authenticationToken, command);
    }


    /**
     * Annotate bulk of instances of a phase.
     * 
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project
     * - Project must be active
     * - If phase is a tutorial, the annotation is only checked for correctness
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting
     *                            user
     * @param commands
     *                            List of annotations
     */
    @PostMapping(value = "/annotate/usepair/bulk")
    public void annotateBulkUsepair(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final List<AddUsePairJudgementCommand> commands) {
        this.judgementApplicationService.annotateBulk(authenticationToken,
                commands.stream().map(c -> (IAddJudgementCommand) c).collect(Collectors.toList()));
    }

    /**
     * Annotate bulk of instances of a phase.
     *
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project
     * - Project must be active
     * - If phase is a tutorial, the annotation is only checked for correctness
     *
     * @param authenticationToken
     *                            The authentication token of the requesting
     *                            user
     * @param commands
     *                            List of annotations
     */
    @PostMapping(value = "/annotate/userank/bulk")
    public void annotateBulkUserank(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final List<AddUseRankJudgementCommand> commands) {
        this.judgementApplicationService.annotateBulk(authenticationToken,
                commands.stream().map(c -> (IAddJudgementCommand) c).collect(Collectors.toList()));
    }
    /**
     * Annotate bulk of instances of a phase.
     *
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project
     * - Project must be active
     * - If phase is a tutorial, the annotation is only checked for correctness
     *
     * @param authenticationToken
     *                            The authentication token of the requesting
     *                            user
     * @param commands
     *                            List of annotations
     */
    @PostMapping(value = "/annotate/userankrelative/bulk")
    public void annotateBulkUserankrelative(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final List<AddUseRankRelativeJudgementCommand> commands) {
        this.judgementApplicationService.annotateBulk(authenticationToken,
                commands.stream().map(c -> (IAddJudgementCommand) c).collect(Collectors.toList()));
    }

    /**
     * Annotate bulk of instances of a phase.
     *
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project
     * - Project must be active
     * - If phase is a tutorial, the annotation is only checked for correctness
     *
     * @param authenticationToken
     *                            The authentication token of the requesting
     *                            user
     * @param commands
     *                            List of annotations
     */
    @PostMapping(value = "/annotate/userankpair/bulk")
    public void annotateBulkUserankbulk(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final List<AddUseRankPairJudgementCommand> commands) {
        this.judgementApplicationService.annotateBulk(authenticationToken,
                commands.stream().map(c -> (IAddJudgementCommand) c).collect(Collectors.toList()));
    }



    /**
     * Annote a specific instance of a phase for WSSIM
     * 
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project, and
     * - Project must be active
     * - Phase must not be a tutorial
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param command
     */
    @PostMapping(value = "/annotate/wssim")
    public void annotateWssim(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final AddWSSIMJudgementCommand command) {
        this.judgementApplicationService.annotate(authenticationToken, command);
    }

    /**
     * Annotate bulk of instances of a phase for WSSIM
     * 
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project
     * - Project must be active
     * - If phase is a tutorial, the annotation is only checked for correctness
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting
     *                            user
     * @param commands
     */
    @PostMapping(value = "/annotate/wssim/bulk")
    public void annotateBulkWssim(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final List<AddWSSIMJudgementCommand> commands) {
        this.judgementApplicationService.annotateBulk(authenticationToken,
                commands.stream().map(c -> (IAddJudgementCommand) c).collect(Collectors.toList()));
    }

    /**
     * Annote a specific instance of a phase for LexSub
     * 
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project, and
     * - Project must be active
     * - Phase must not be a tutorial
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param command
     */
    @PostMapping(value = "/annotate/lexsub")
    public void annotateLexSub(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final AddLexSubJudgementCommand command) {
        this.judgementApplicationService.annotate(authenticationToken, command);
    }

    /**
     * Annote a specific instance of a phase for Sentiment
     *
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project, and
     * - Project must be active
     * - Phase must not be a tutorial
     *
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param command
     */
    @PostMapping(value = "/annotate/sentiment")
    public void annotateSentiment(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final AddSentimentAndChoiceJudgementCommand command) {
        this.judgementApplicationService.annotate(authenticationToken, command);
    }

    /**
     * Annotate bulk of instances of a phase for lexsub
     * 
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project
     * - Project must be active
     * - If phase is a tutorial, the annotation is only checked for correctness
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting
     *                            user
     * @param commands
     */
    @PostMapping(value = "/annotate/lexsub/bulk")
    public void annotateBulkLexSub(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final List<AddLexSubJudgementCommand> commands) {
        this.judgementApplicationService.annotateBulk(authenticationToken,
                commands.stream().map(c -> (IAddJudgementCommand) c).collect(Collectors.toList()));
    }

    /**
     * Annotate bulk of instances of a phase for sentiment
     *
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project
     * - Project must be active
     * - If phase is a tutorial, the annotation is only checked for correctness
     *
     * @param authenticationToken
     *                            The authentication token of the requesting
     *                            user
     * @param commands
     */
    @PostMapping(value = "/annotate/sentiment/bulk")
    public void annotateBulkSentiment(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final List<AddSentimentAndChoiceJudgementCommand> commands) {
        this.judgementApplicationService.annotateBulk(authenticationToken,
                commands.stream().map(c -> (IAddJudgementCommand) c).collect(Collectors.toList()));
    }


    /**
     * count all attempted judgement by annotator of given phase

     *
     * @param authenticationToken token
     *
     * @param
     */
    @GetMapping(value = "/count")
    public int countAttemptedJudgement(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam (value = "owner") final String owner,
            @RequestParam (value = "project") final String project,
            @RequestParam (value = "phase") final String phase)

    {
        return this.judgementApplicationService.countAttemptedJudgements(authenticationToken,
                owner, project, phase);
    }

    // all tutorial api's
    /**
     * Get the tutorial judgement dto of a specific phase.
     * Underlying DTO depends on the annotation type of the phase and should be
     * resolved by the client.
     *
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project, or
     * - Project must be public
     * - If project is not active, only the owner can see the phases
     * - If the phase is a tutorial, only the owner and admins can see the phase +
     * data
     *
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param owner
     *                            The owner of the project
     * @param project
     *                            The name of the project
     * @param phase
     *                            The name of the phase
     * @return
     *         Phase data
     */
    @GetMapping("/tutorial")
    public List<IJudgementDto> getTutorialJudgementDtos(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase) {
        return this.judgementApplicationService.getTutorialJudgementDto(authenticationToken, owner, project, phase);
    }

    /**
     * Get the  tutorial judgement dto of a specific phase as a paged result.
     *
     * @param authenticationToken
     * @param owner
     * @param project
     * @param phase
     * @param page
     * @return
     */
    @GetMapping(value = "tutorial/paged")
    public PagedJudgementDto getPagedTutoralJudgementDtos(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase,
            @RequestParam(value = "page") final int page) {
        return this.judgementApplicationService.getTutorialPagedJudgementDto(authenticationToken, owner, project, phase, page,
                50, null);
    }

    /**
     * Get personal tutorial history of a specific phase.
     * Underlying DTO depends on the annotation type of the phase and should be
     * resolved by the client
     *
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project, and
     * - Phase must not be a tutorial
     * - If project is not active, only the owner can see the history
     *
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param owner
     *                            The owner of the project
     * @param project
     *                            The name of the project
     * @param phase
     *                            The name of the phase
     * @return
     *         Phase data
     */
    @GetMapping(value = "/tutorial/history/personal")
    public List<IJudgementDto> getTutorialHistory(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase) {
        return this.judgementApplicationService.getTutorialHistory(authenticationToken, owner, project, phase);
    }

    /**
     * Get personal tutorial history of a specific phase as a paged result.
     *
     * @param authenticationToken
     * @param owner
     * @param project
     * @param phase
     * @param page
     * @return
     */
    @GetMapping(value = "tutorial/history/personal/paged")
    public PagedJudgementDto getPagedTutorialHistory(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase,
            @RequestParam(value = "page") final int page) {
        return this.judgementApplicationService.getPagedTutorialHistory(authenticationToken, owner, project, phase, page, 50,
                null);
    }


}
