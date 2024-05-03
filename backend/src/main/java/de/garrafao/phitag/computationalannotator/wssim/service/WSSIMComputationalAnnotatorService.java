package de.garrafao.phitag.computationalannotator.wssim.service;

import de.garrafao.phitag.application.annotationtype.data.AnnotationTypeEnum;
import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.instance.data.IInstanceDto;
import de.garrafao.phitag.application.instance.wssiminstance.WSSIMInstanceApplicationService;
import de.garrafao.phitag.application.judgement.wssimjudgement.WSSIMJudgementApplicationService;
import de.garrafao.phitag.application.judgement.wssimjudgement.data.AddWSSIMJudgementCommand;
import de.garrafao.phitag.application.phase.data.TutorialHistoryDto;
import de.garrafao.phitag.computationalannotator.common.command.ComputationalAnnotatorCommand;
import de.garrafao.phitag.computationalannotator.common.function.LatestTutorialHistoryFunction;
import de.garrafao.phitag.computationalannotator.common.model.application.data.OpenAPIResponseDto;
import de.garrafao.phitag.computationalannotator.wssim.data.WSSIMComputationalAnnotatotInstanceDto;
import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.user.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class WSSIMComputationalAnnotatorService {

    private final WSSIMInstanceApplicationService wssimInstanceApplicationService;

    private final WSSIMJudgementApplicationService wssimJudgementApplicationService;

    private final WSSIMOpenAIService wssimOpenAIService;

    private final LatestTutorialHistoryFunction latestTutorialHistoryFunction;

    private final CommonService commonService;

    public WSSIMComputationalAnnotatorService(WSSIMInstanceApplicationService wssimInstanceApplicationService, WSSIMJudgementApplicationService wssimJudgementApplicationService, WSSIMOpenAIService wssimOpenAIService, LatestTutorialHistoryFunction latestTutorialHistoryFunction, CommonService commonService) {
        this.wssimInstanceApplicationService = wssimInstanceApplicationService;
        this.wssimJudgementApplicationService = wssimJudgementApplicationService;
        this.wssimOpenAIService = wssimOpenAIService;
        this.latestTutorialHistoryFunction = latestTutorialHistoryFunction;
        this.commonService = commonService;
    }

    @Transactional
    public  void wssimChatGptAnnotation(final String authenticationToken, final ComputationalAnnotatorCommand command) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(command.getOwner(), command.getProject(), command.getPhase());

        List<IInstanceDto> instanceDtos = new ArrayList<>();

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_WSSIM.name())) {
            this.wssimInstanceApplicationService.findByPhase(phaseEntity)
                    .forEach(wssimInstance -> instanceDtos.add(WSSIMComputationalAnnotatotInstanceDto.from(wssimInstance)));
        }
        Annotator annotator = this.commonService.getAnnotator(command.getOwner(), command.getProject(), "ChatGpt");
        for (IInstanceDto instanceDto : instanceDtos) {
            if (instanceDto instanceof WSSIMComputationalAnnotatotInstanceDto) {
                WSSIMComputationalAnnotatotInstanceDto wssimDto = (WSSIMComputationalAnnotatotInstanceDto) instanceDto;

                // Access and use data from UsePairComputationalAnnotatotInstanceDto
                String usage = wssimDto.getUsage().getContext();
                final OpenAPIResponseDto response = this.wssimOpenAIService.chat(
                        command.getApiKey(),
                        command.getModel(),
                        command.getPrompt(),
                        usage,
                       wssimDto.getUsage().getLemma()
                );
                AddWSSIMJudgementCommand wssimJudgementCommand= new AddWSSIMJudgementCommand(
                        command.getOwner(), command.getProject(), command.getPhase(), wssimDto.getId().getInstanceId(),
                        response.getJudgement(), ""
                );
                this.wssimJudgementApplicationService.annotate(phaseEntity,annotator, wssimJudgementCommand);
            }
        }

    }


    @Transactional
    public List<TutorialHistoryDto> wssimChatGptTutorial(final String authenticationToken, final ComputationalAnnotatorCommand command) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(command.getOwner(), command.getProject(), command.getPhase());

        List<TutorialHistoryDto> tutorialHistoryDtos = new ArrayList<>();

        for (Phase phase : phaseEntity.getTutorialRequirements()) {
            tutorialHistoryDtos.add(this.annotate(authenticationToken, command.getOwner(), command.getProject(), phase, command));
        }

        return tutorialHistoryDtos;
    }

    public TutorialHistoryDto annotate(final String authenticationToken,
                                       final String owner,
                                       final String project,
                                       final Phase phase,
                                       final ComputationalAnnotatorCommand command) {
        List<IInstanceDto> instanceDtos = new ArrayList<>();

        if (phase.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            this.wssimInstanceApplicationService.findByPhase(phase)
                    .forEach(wssimInstance -> instanceDtos
                            .add(WSSIMComputationalAnnotatotInstanceDto.from(wssimInstance)));
        }

        Annotator annotator = this.commonService.getAnnotator(command.getOwner(),
                command.getProject(),
                "ChatGpt");
        List<AddWSSIMJudgementCommand> addWSSIMJudgementCommands = new ArrayList<>();

        for (IInstanceDto instanceDto : instanceDtos) {
            if (instanceDto instanceof WSSIMComputationalAnnotatotInstanceDto) {
                WSSIMComputationalAnnotatotInstanceDto wssimDTO= (WSSIMComputationalAnnotatotInstanceDto) instanceDto;

                String usage = wssimDTO.getUsage().getContext();
                String lemma = wssimDTO.getUsage().getLemma();

                final OpenAPIResponseDto response = this.wssimOpenAIService.chat(
                        command.getApiKey(),
                        command.getModel(),
                        command.getPrompt(),
                        usage,
                        lemma
                );
                AddWSSIMJudgementCommand wssimJudgementCommand= new AddWSSIMJudgementCommand(
                        command.getOwner(),
                        command.getProject(),
                        command.getPhase(),
                        wssimDTO.getId().getInstanceId(),
                        response.getJudgement(),
                        ""
                );

                addWSSIMJudgementCommands.add(wssimJudgementCommand);
            }
        }
        this.wssimJudgementApplicationService.annotateBulk(phase, annotator, addWSSIMJudgementCommands);

        final List<TutorialHistoryDto> tutorialHistoryDtos = this.latestTutorialHistoryFunction.
                getTutorialMeasureHistory(phase);
        return this.latestTutorialHistoryFunction.getLatestTutorialHistory(tutorialHistoryDtos);
    }

}
