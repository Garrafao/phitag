package de.garrafao.phitag.computationalannotator.sentiment.service;

import de.garrafao.phitag.application.annotationtype.data.AnnotationTypeEnum;
import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.instance.data.IInstanceDto;
import de.garrafao.phitag.application.instance.sentimentandchoice.SentimentAndChoiceInstanceApplicationService;
import de.garrafao.phitag.application.judgement.sentimentandchoice.SentimentAndChoiceJudgementApplicationService;
import de.garrafao.phitag.application.judgement.sentimentandchoice.data.AddSentimentAndChoiceJudgementCommand;
import de.garrafao.phitag.application.phase.data.TutorialHistoryDto;
import de.garrafao.phitag.computationalannotator.common.command.ComputationalAnnotatorCommand;
import de.garrafao.phitag.computationalannotator.common.function.CommonFunction;
import de.garrafao.phitag.computationalannotator.sentiment.data.SentimentComputationalAnnotatotInstanceDto;
import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.judgement.sentimentandchoice.SentimentAndChoiceJudgement;
import de.garrafao.phitag.domain.judgement.sentimentandchoice.SentimentAndChoiceJudgementRepository;
import de.garrafao.phitag.domain.judgement.sentimentandchoice.query.SentimentAndChoiceJudgementQueryBuilder;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.statistic.tutorialannotationmeasurehistory.TutorialAnnotationMeasureHistoryRepository;
import de.garrafao.phitag.domain.user.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SentimentComputationalAnnotatorService {


    private final SentimentAndChoiceInstanceApplicationService sentimentAndChoiceInstanceApplicationService;

    private final SentimentAndChoiceJudgementApplicationService sentimentAndChoiceJudgementApplicationService;

    private final SentimentOpenAIService sentimentOpenAIService;

    private final SentimentAndChoiceJudgementRepository sentimentAndChoiceJudgementRepository;

    private final CommonFunction commonFunction;


    private final TutorialAnnotationMeasureHistoryRepository tutorialAnnotationMeasureHistoryRepository;

    private final CommonService commonService;

    public SentimentComputationalAnnotatorService(SentimentAndChoiceInstanceApplicationService sentimentAndChoiceInstanceApplicationService,
                                                  SentimentAndChoiceJudgementApplicationService sentimentAndChoiceJudgementApplicationService,
                                                  SentimentOpenAIService sentimentOpenAIService,
                                                  SentimentAndChoiceJudgementRepository sentimentAndChoiceJudgementRepository,
                                                  CommonFunction commonFunction, TutorialAnnotationMeasureHistoryRepository tutorialAnnotationMeasureHistoryRepository, CommonService commonService) {
        this.sentimentAndChoiceInstanceApplicationService = sentimentAndChoiceInstanceApplicationService;
        this.sentimentAndChoiceJudgementApplicationService = sentimentAndChoiceJudgementApplicationService;
        this.sentimentOpenAIService = sentimentOpenAIService;
        this.sentimentAndChoiceJudgementRepository = sentimentAndChoiceJudgementRepository;
        this.commonFunction = commonFunction;
        this.tutorialAnnotationMeasureHistoryRepository = tutorialAnnotationMeasureHistoryRepository;
        this.commonService = commonService;
    }

    @Transactional
    public void sentimentGptAnnotation(final String authenticationToken, final ComputationalAnnotatorCommand command) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(command.getOwner(), command.getProject(), command.getPhase());

        List<IInstanceDto> instanceDtos = new ArrayList<>();

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SENTIMENT.name())) {
            this.sentimentAndChoiceInstanceApplicationService.findByPhase(phaseEntity)
                    .forEach(instance -> instanceDtos.add(SentimentComputationalAnnotatotInstanceDto.from(instance)));
        }

        Annotator annotator = this.commonService.getAnnotator(command.getOwner(), command.getProject(), "ChatGpt");

        for (IInstanceDto instanceDto : instanceDtos) {
            if (instanceDto instanceof SentimentComputationalAnnotatotInstanceDto) {
                SentimentComputationalAnnotatotInstanceDto sentimentComputationalAnnotatotInstanceDto = (SentimentComputationalAnnotatotInstanceDto) instanceDto;

                // Access and use data from UsePairComputationalAnnotatotInstanceDto
                String usage = sentimentComputationalAnnotatotInstanceDto.getUsage().getContext();
                String lemma = sentimentComputationalAnnotatotInstanceDto.getUsage().getLemma();
                final String response = this.sentimentOpenAIService.chat(
                        command.getApiKey(),
                        command.getModel(),
                        command.getTemperature(),
                        command.getTopP(),
                        command.getPrompt(),
                        command.getSystem(),
                        command.getFinalMessage(),
                        usage,
                        lemma

                );
                AddSentimentAndChoiceJudgementCommand command1 = new AddSentimentAndChoiceJudgementCommand(
                        command.getOwner(), command.getProject(),
                        command.getPhase(), sentimentComputationalAnnotatotInstanceDto.getId().getInstanceId(),
                        response,
                        ""
                );
                this.sentimentAndChoiceJudgementApplicationService.annotate(phaseEntity, annotator, command1);
            }
        }

    }

    @Transactional
    public List<TutorialHistoryDto> sentimentChatGptTutorial(final String authenticationToken, final ComputationalAnnotatorCommand command) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(command.getOwner(), command.getProject(), command.getPhase());

        List<TutorialHistoryDto> tutorialHistoryDtos = new ArrayList<>();

        for (Phase phase : phaseEntity.getTutorialRequirements()) {
            tutorialHistoryDtos.add(this.annotate(phase, command));
        }

        return tutorialHistoryDtos;
    }

    public TutorialHistoryDto annotate(final Phase phase, final ComputationalAnnotatorCommand command) {
        List<IInstanceDto> annotatableInstances = new ArrayList<>();

        if (phase.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            this.sentimentAndChoiceInstanceApplicationService.findByPhase(phase)
                    .forEach(instance -> annotatableInstances
                            .add(SentimentComputationalAnnotatotInstanceDto.from(instance)));
        }

        Annotator annotator = this.commonService.getAnnotator(command.getOwner(), command.getProject(), "ChatGpt");
        List<AddSentimentAndChoiceJudgementCommand> sentimentAndChoiceJudgementCommands = new ArrayList<>();

        for (IInstanceDto instanceDto : annotatableInstances) {
            if (instanceDto instanceof SentimentComputationalAnnotatotInstanceDto) {
                SentimentComputationalAnnotatotInstanceDto dto = (SentimentComputationalAnnotatotInstanceDto) instanceDto;

                String usage = dto.getUsage().getContext();
                String lemma = dto.getUsage().getLemma();

                try {
                    final String response = this.sentimentOpenAIService.chat(
                            command.getApiKey(),
                            command.getModel(),
                            command.getTemperature(),
                            command.getTopP(),
                            command.getPrompt(),
                            command.getSystem(),
                            command.getFinalMessage(),
                            usage,
                            lemma
                    );

                    AddSentimentAndChoiceJudgementCommand judgementCommand = new AddSentimentAndChoiceJudgementCommand(
                            command.getOwner(),
                            command.getProject(),
                            command.getPhase(),
                            dto.getId().getInstanceId(),
                            response,
                            ""
                    );
                    sentimentAndChoiceJudgementCommands.add(judgementCommand);

                } catch (Exception e) {
                    throw new RuntimeException("Interrupted while waiting to make API call", e);
                }
            }
        }

        this.sentimentAndChoiceJudgementApplicationService.annotateBulk(phase, annotator, sentimentAndChoiceJudgementCommands);

        final List<TutorialHistoryDto> tutorialHistoryDtos = this.getTutorialMeasureHistory(phase);
        return this.getLatestTutorialHistory(tutorialHistoryDtos);
    }

    private List<TutorialHistoryDto> getTutorialMeasureHistory(final Phase phase) {

      return this.tutorialAnnotationMeasureHistoryRepository.findByIdPhaseid(phase.getId()).stream()
                .map(TutorialHistoryDto::from).toList();
    }

        private  TutorialHistoryDto getLatestTutorialHistory(List<TutorialHistoryDto> tutorialHistoryDtos) {
            TutorialHistoryDto latestTutorialHistory = null;

            for (TutorialHistoryDto tutorialHistoryDto : tutorialHistoryDtos) {
                if (latestTutorialHistory == null || tutorialHistoryDto.getTimestamp().compareTo(latestTutorialHistory.getTimestamp()) > 0) {
                    latestTutorialHistory = tutorialHistoryDto;
                }
            }

            return latestTutorialHistory;
        }

        public void tinyLLMAnnotate(final String authenticationToken, final ComputationalAnnotatorCommand command){
            final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
            final Phase phaseEntity = this.commonService.getPhase(command.getOwner(), command.getProject(), command.getPhase());

            List<IInstanceDto> instanceDtos = new ArrayList<>();

            if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SENTIMENT.name())) {
                this.sentimentAndChoiceInstanceApplicationService.findByPhase(phaseEntity)
                        .forEach(instance -> instanceDtos.add(SentimentComputationalAnnotatotInstanceDto.from(instance)));
            }

            Annotator annotator = this.commonService.getAnnotator(command.getOwner(), command.getProject(), "TinyLLM");

            for (IInstanceDto instanceDto : instanceDtos) {
                if (instanceDto instanceof SentimentComputationalAnnotatotInstanceDto) {
                    SentimentComputationalAnnotatotInstanceDto sentimentComputationalAnnotatotInstanceDto = (SentimentComputationalAnnotatotInstanceDto) instanceDto;


                    List<SentimentAndChoiceJudgement> gold = this.findByPhase(sentimentComputationalAnnotatotInstanceDto.getId().getInstanceId());
                    String res = gold.get(0).getLabel();

                    // Access and use data from UsePairComputationalAnnotatotInstanceDto
                    String usage = sentimentComputationalAnnotatotInstanceDto.getUsage().getContext();
                    String lemma = sentimentComputationalAnnotatotInstanceDto.getUsage().getLemma();
                    AddSentimentAndChoiceJudgementCommand command1 = new AddSentimentAndChoiceJudgementCommand(
                            command.getOwner(), command.getProject(),
                            command.getPhase(), sentimentComputationalAnnotatotInstanceDto.getId().getInstanceId(),
                            res,
                            ""
                    );
                    this.sentimentAndChoiceJudgementApplicationService.annotate(phaseEntity, annotator, command1);
                }
            }


        }

    public List<SentimentAndChoiceJudgement> findByPhase( final String instacneId) {
        final Query query = new SentimentAndChoiceJudgementQueryBuilder()
                .withOwner("Example-User")
                .withProject("Example-Project-Tag")
                .withPhase("tutorial")
                .withInstanceid(instacneId)
                .withAnnotator("Example-User")
                .build();
        return this.sentimentAndChoiceJudgementRepository.findByQuery(query);
    }

}
