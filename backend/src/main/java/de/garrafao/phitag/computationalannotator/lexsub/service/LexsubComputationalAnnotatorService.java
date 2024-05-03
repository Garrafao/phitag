package de.garrafao.phitag.computationalannotator.lexsub.service;

import de.garrafao.phitag.application.annotationtype.data.AnnotationTypeEnum;
import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.instance.data.IInstanceDto;
import de.garrafao.phitag.application.instance.lexsubinstance.LexSubInstanceApplicationService;
import de.garrafao.phitag.application.judgement.lexsubjudgement.LexSubJudgementApplicationService;
import de.garrafao.phitag.application.judgement.lexsubjudgement.data.AddLexSubJudgementCommand;
import de.garrafao.phitag.application.phase.data.TutorialHistoryDto;
import de.garrafao.phitag.computationalannotator.common.command.ComputationalAnnotatorCommand;
import de.garrafao.phitag.computationalannotator.common.function.LatestTutorialHistoryFunction;
import de.garrafao.phitag.computationalannotator.common.model.application.data.OpenAPIResponseDto;
import de.garrafao.phitag.computationalannotator.lexsub.data.LexsubComputationalAnnotatotInstanceDto;
import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.user.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class LexsubComputationalAnnotatorService {

    private final LexSubInstanceApplicationService lexSubInstanceApplicationService;

    private final LexSubJudgementApplicationService lexSubJudgementApplicationService;

    private final LexsubOpenAIService lexsubOpenAIService;

    private final LatestTutorialHistoryFunction latestTutorialHistoryFunction;

    private final CommonService commonService;

    public LexsubComputationalAnnotatorService(LexSubInstanceApplicationService lexSubInstanceApplicationService,
                                               LexSubJudgementApplicationService lexSubJudgementApplicationService,
                                               LexsubOpenAIService lexsubOpenAIService, LatestTutorialHistoryFunction latestTutorialHistoryFunction, CommonService commonService) {
        this.lexSubInstanceApplicationService = lexSubInstanceApplicationService;
        this.lexSubJudgementApplicationService = lexSubJudgementApplicationService;
        this.lexsubOpenAIService = lexsubOpenAIService;
        this.latestTutorialHistoryFunction = latestTutorialHistoryFunction;
        this.commonService = commonService;
    }

    @Transactional
    public  void lexsubChatGptAnnotation(final String authenticationToken, final ComputationalAnnotatorCommand command) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(command.getOwner(), command.getProject(), command.getPhase());

        List<IInstanceDto> instanceDtos = new ArrayList<>();

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_LEXSUB.name())) {
            this.lexSubInstanceApplicationService.findByPhase(phaseEntity)
                    .forEach(lexSubInstance -> instanceDtos.add(LexsubComputationalAnnotatotInstanceDto.from(lexSubInstance)));
        }
        Annotator annotator = this.commonService.getAnnotator(command.getOwner(), command.getProject(), "ChatGpt");
        for (IInstanceDto instanceDto : instanceDtos) {
            if (instanceDto instanceof LexsubComputationalAnnotatotInstanceDto) {

                LexsubComputationalAnnotatotInstanceDto lexsubDto = (LexsubComputationalAnnotatotInstanceDto) instanceDto;

                final OpenAPIResponseDto response = this.lexsubOpenAIService.chat(
                        command.getApiKey(),
                        command.getModel(),
                        command.getPrompt(),
                        lexsubDto.getUsage().getContext(),
                        lexsubDto.getUsage().getLemma()
                );
                AddLexSubJudgementCommand lexSubJudgementCommand = new AddLexSubJudgementCommand(
                        command.getOwner(), command.getProject(), command.getPhase(), lexsubDto.getId().getInstanceId(), response.getJudgement(), ""
                );
                this.lexSubJudgementApplicationService.annotate(phaseEntity,annotator, lexSubJudgementCommand);
            }
        }

    }

    @Transactional
    public List<TutorialHistoryDto> lexsubChatGptTutorial(final String authenticationToken, final ComputationalAnnotatorCommand command) {
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

        if (phase.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_LEXSUB.name())) {
            this.lexSubInstanceApplicationService.findByPhase(phase)
                    .forEach(lexSubInstance -> instanceDtos
                            .add(LexsubComputationalAnnotatotInstanceDto.from(lexSubInstance)));
        }

        Annotator annotator = this.commonService.getAnnotator(command.getOwner(),
                command.getProject(),
                "ChatGpt");
        List<AddLexSubJudgementCommand> addLexSubJudgementCommands = new ArrayList<>();

        for (IInstanceDto instanceDto : instanceDtos) {
            if (instanceDto instanceof LexsubComputationalAnnotatotInstanceDto) {
               LexsubComputationalAnnotatotInstanceDto lexsubDTO= (LexsubComputationalAnnotatotInstanceDto) instanceDto;

                String usage = lexsubDTO.getUsage().getContext();
                String lemma = lexsubDTO.getUsage().getLemma();

                final OpenAPIResponseDto response = this.lexsubOpenAIService.chat(
                        command.getApiKey(),
                        command.getModel(),
                        command.getPrompt(),
                        usage,
                        lemma
                );
                AddLexSubJudgementCommand addLexSubJudgementCommand= new AddLexSubJudgementCommand(
                        command.getOwner(),
                        command.getProject(),
                        command.getPhase(),
                        lexsubDTO.getId().getInstanceId(),
                        response.getJudgement(),
                        ""
                );

                addLexSubJudgementCommands.add(addLexSubJudgementCommand);
            }
        }
        this.lexSubJudgementApplicationService.annotateBulk(phase, annotator, addLexSubJudgementCommands);

        final List<TutorialHistoryDto> tutorialHistoryDtos = this.latestTutorialHistoryFunction.
                getTutorialMeasureHistory(phase);
        return this.latestTutorialHistoryFunction.getLatestTutorialHistory(tutorialHistoryDtos);
    }




}
