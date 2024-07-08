package de.garrafao.phitag.application.statistics.annotatorhistorytable;

import de.garrafao.phitag.application.annotationtype.data.AnnotationTypeEnum;
import de.garrafao.phitag.application.common.CommonMathService;
import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.judgement.data.IJudgementDto;
import de.garrafao.phitag.application.judgement.lexsubjudgement.LexSubJudgementApplicationService;
import de.garrafao.phitag.application.judgement.lexsubjudgement.data.LexJudgementDto;
import de.garrafao.phitag.application.judgement.sentimentandchoice.SentimentAndChoiceJudgementApplicationService;
import de.garrafao.phitag.application.judgement.sentimentandchoice.data.SentimentAndChoiceJudgementDto;
import de.garrafao.phitag.application.judgement.usepairjudgement.UsePairJudgementApplicationService;
import de.garrafao.phitag.application.judgement.usepairjudgement.data.UsePairJudgementDto;
import de.garrafao.phitag.application.judgement.userankjudgement.UseRankJudgementApplicationService;
import de.garrafao.phitag.application.judgement.userankjudgement.data.UseRankJudgementDto;
import de.garrafao.phitag.application.judgement.userankpairjudgement.UseRankPairJudgementApplicationService;
import de.garrafao.phitag.application.judgement.userankpairjudgement.data.UseRankPairJudgementDto;
import de.garrafao.phitag.application.judgement.userankrelativejudgement.UseRankRelativeJudgementApplicationService;
import de.garrafao.phitag.application.judgement.userankrelativejudgement.data.UseRankRelativeJudgementDto;
import de.garrafao.phitag.application.judgement.wssimjudgement.WSSIMJudgementApplicationService;
import de.garrafao.phitag.application.judgement.wssimjudgement.data.WSSIMJudgementDto;
import de.garrafao.phitag.application.statistics.annotatorhistorytable.data.command.AddAnnotatorStaticHistoryCommand;
import de.garrafao.phitag.application.statistics.annotatorhistorytable.data.dto.AnnotatorStatisticHistoryDto;
import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.statistic.annotatorstathistory.AnnotatorHistoryTable;
import de.garrafao.phitag.domain.statistic.annotatorstathistory.AnnotatorHistoryTableRepository;
import de.garrafao.phitag.domain.statistic.error.StatisticException;
import de.garrafao.phitag.domain.statistic.statisticannotationmeasure.StatisticAnnotationMeasure;
import de.garrafao.phitag.domain.statistic.statisticannotationmeasure.StatisticAnnotationMeasureEnum;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnotatorStatisticHistoryApplicationService {


    private final UseRankJudgementApplicationService useRankJudgementApplicationService;
    private final UseRankRelativeJudgementApplicationService useRankRelativeJudgementApplicationService;

    private final SentimentAndChoiceJudgementApplicationService sentimentAndChoiceJudgementApplicationService;

    private final LexSubJudgementApplicationService lexSubJudgementApplicationService;

    private final  UseRankPairJudgementApplicationService useRankPairJudgementApplicationService;

    private final WSSIMJudgementApplicationService wssimJudgementApplicationService;
    private final CommonService commonService;
    private final AnnotatorHistoryTableRepository annotatorHistoryTableRepository;

    private final CommonMathService commonMathService;

    private final UsePairJudgementApplicationService usePairJudgementApplicationService;

    public AnnotatorStatisticHistoryApplicationService(UseRankJudgementApplicationService useRankJudgementApplicationService,
                                                       UseRankPairJudgementApplicationService useRankPairJudgementApplicationService,
                                                       UseRankRelativeJudgementApplicationService useRankRelativeJudgementApplicationService,
                                                       SentimentAndChoiceJudgementApplicationService sentimentAndChoiceJudgementApplicationService,
                                                       LexSubJudgementApplicationService lexSubJudgementApplicationService,
                                                       WSSIMJudgementApplicationService wssimJudgementApplicationService,
                                                       CommonService commonService,
                                                       AnnotatorHistoryTableRepository annotatorHistoryTableRepository, CommonMathService commonMathService, UsePairJudgementApplicationService usePairJudgementApplicationService) {
        this.useRankJudgementApplicationService = useRankJudgementApplicationService;
        this.useRankPairJudgementApplicationService = useRankPairJudgementApplicationService;
        this.useRankRelativeJudgementApplicationService = useRankRelativeJudgementApplicationService;
        this.sentimentAndChoiceJudgementApplicationService = sentimentAndChoiceJudgementApplicationService;
        this.lexSubJudgementApplicationService = lexSubJudgementApplicationService;
        this.wssimJudgementApplicationService = wssimJudgementApplicationService;
        this.commonService = commonService;
        this.annotatorHistoryTableRepository = annotatorHistoryTableRepository;
        this.commonMathService = commonMathService;
        this.usePairJudgementApplicationService = usePairJudgementApplicationService;
    }



    public List<AnnotatorStatisticHistoryDto> fetchAll(final String authenticationToken, final String annotatorname,
                                                       final String ownername, final String projectname, final String phasename) {

        List<AnnotatorHistoryTable> historyTables = this.annotatorHistoryTableRepository
                .findByAnnotatornameAndOwnernameAndProjectnameAndPhasename(annotatorname, ownername, projectname, phasename);
        return historyTables.stream()
                .map(AnnotatorStatisticHistoryDto::from).collect(Collectors.toList());

    }

    @Transactional
    public void save(final String authenticationToken, final AddAnnotatorStaticHistoryCommand command) {

        double agreeement = this.calculateAgreeement(command.getAnnotatorname(), command.getGoldannotator(),command.getOwnername()
        ,command.getProjectname(), command.getPhasename(), command.getStatisticAnnotationMeasure());

        annotatorHistoryTableRepository.save(
                new AnnotatorHistoryTable(
                        String.valueOf(System.currentTimeMillis()),
                        command.getAnnotatorname(),
                        command.getGoldannotator(),
                        command.getOwnername(),
                        command.getProjectname(),
                        command.getPhasename(),
                        command.getStatisticAnnotationMeasure().getName(),
                        agreeement));

    }
    private double calculateAgreeement(final String annotatorname, final String goldannotator, final String owner,
                                     final String project, final String phase, final StatisticAnnotationMeasure statisticAnnotationMeasure) {


        List<String> annotatorInstanceId = this.getJudgementDto(annotatorname, owner, project, phase).stream()
                .map(judgement -> judgement.getId().getInstanceId())
                .collect(Collectors.toList());

        List<String> goldInstanceId = this.getJudgementDto(goldannotator, owner, project, phase).stream()
                .map(judgement -> judgement.getId().getInstanceId())
                .collect(Collectors.toList());

        List<String> intersectionInstanceId = annotatorInstanceId.stream()
                .filter(goldInstanceId::contains)
                .collect(Collectors.toList());

        // Check if the intersection is empty or not
        if (intersectionInstanceId.isEmpty()) {
            throw new StatisticException("No common instance IDs found between annotators.");
        }

        // Fetch judgments matching the intersection instance IDs for the annotator
        List<String> annotatorLabels = this.getJudgementDto(annotatorname, owner, project, phase).stream()
                .filter(judgement -> intersectionInstanceId.contains(judgement.getId().getInstanceId()))
                .map(judgement -> judgement.getLabel())
                .collect(Collectors.toList());

        // Fetch judgments matching the intersection instance IDs for the gold annotator
        List<String> goldLabels = this.getJudgementDto(goldannotator, owner, project, phase).stream()
                .filter(judgement -> intersectionInstanceId.contains(judgement.getId().getInstanceId()))
                .map(judgement -> judgement.getLabel())
                .collect(Collectors.toList());


        //find lable set
        List<IJudgementDto> labelset = this.getJudgementDto(goldannotator, owner, project, phase);


        List<String> categories = this.getLabelSet(owner, project, phase);



        List<List<String>> annotatorLabelList = Arrays.asList(goldLabels, annotatorLabels);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);
        // Calculate the annotator agreement
        double agreement = this.commonMathService.calculateAnnotatorAgreement(categories,
                StatisticAnnotationMeasureEnum.fromId(statisticAnnotationMeasure.getId()),
                annotatorLabelList);
        return agreement;
    }


    private final List<IJudgementDto> getJudgementDto(final String annotatorname,  final String owner,
                                               final String project, final String phase) {

        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        Annotator annotator = this.commonService.getAnnotator(phaseEntity.getId().getProjectid().getOwnername(),
                phaseEntity.getId().getProjectid().getName(), annotatorname);


        List<IJudgementDto> judgementDtos = new ArrayList<>();

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            this.usePairJudgementApplicationService.findByPhaseAndAnnotator(phaseEntity, annotator).forEach(
                    usePairJudgement -> judgementDtos.add(UsePairJudgementDto.from(usePairJudgement)));
        }else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK.name())) {
            this.useRankJudgementApplicationService.findByPhaseAndAnnotator(phaseEntity, annotator).forEach(
                    useRankJudgement -> judgementDtos.add(UseRankJudgementDto.from(useRankJudgement)));
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_RELATIVE.name())) {
            this.useRankRelativeJudgementApplicationService.findByPhaseAndAnnotator(phaseEntity, annotator).forEach(
                    useRankRelativeJudgement -> judgementDtos.add(UseRankRelativeJudgementDto.from(useRankRelativeJudgement)));
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_PAIR.name())) {
            this.useRankPairJudgementApplicationService.findByPhaseAndAnnotator(phaseEntity, annotator).forEach(
                    useRankPairJudgement -> judgementDtos.add(UseRankPairJudgementDto.from(useRankPairJudgement)));
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_WSSIM.name())) {
            this.wssimJudgementApplicationService.findByPhaseAndAnnotator(phaseEntity, annotator).forEach(
                    wssimJudgement -> judgementDtos.add(WSSIMJudgementDto.from(wssimJudgement)));
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_LEXSUB.name())) {
            this.lexSubJudgementApplicationService.findByPhaseAndAnnotator(phaseEntity, annotator).forEach(
                    lexSubJudgement -> judgementDtos.add(LexJudgementDto.from(lexSubJudgement)));
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SENTIMENT.name()) ||
                phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_CHOICE.name())) {
            this.sentimentAndChoiceJudgementApplicationService.findByPhaseAndAnnotator(phaseEntity, annotator).forEach(
                    sentimentJudgement -> judgementDtos.add(SentimentAndChoiceJudgementDto.from(sentimentJudgement)));
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SPAN.name())) {
            this.sentimentAndChoiceJudgementApplicationService.findByPhaseAndAnnotator(phaseEntity, annotator).forEach(
                    sentimentJudgement -> judgementDtos.add(SentimentAndChoiceJudgementDto.from(sentimentJudgement)));
        }
        return judgementDtos;
    }


    private final List<String> getLabelSet(final String owner, final String project, final String phase) {

        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        List<String> labelSet = new ArrayList<>();

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            this.usePairJudgementApplicationService.findByPhase(phaseEntity).stream().findFirst().ifPresent(
                    usePairJudgement -> labelSet.addAll(usePairJudgement.getUsePairInstance().getLabelSet()));
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK.name())) {
            this.useRankJudgementApplicationService.findByPhase(phaseEntity).stream().findFirst().ifPresent(
                    useRankJudgement -> labelSet.addAll(useRankJudgement.getUseRankInstance().getLabelSet()));
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_PAIR.name())) {
            this.useRankPairJudgementApplicationService.findByPhase(phaseEntity).stream().findFirst().ifPresent(
                    judgement -> labelSet.addAll(judgement.getUseRankPairInstance().getLabelSet()));
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_RELATIVE.name())) {
            this.useRankRelativeJudgementApplicationService.findByPhase(phaseEntity).stream().findFirst().ifPresent(
                    judgement -> labelSet.addAll(judgement.getUseRankRelativeInstance().getLabelSet()));
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_LEXSUB.name())) {
            this.lexSubJudgementApplicationService.findByPhase(phaseEntity).stream().findFirst().ifPresent(
                    judgement -> labelSet.addAll(judgement.getInstance().getLabelSet()));
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SENTIMENT.name())) {
            this.sentimentAndChoiceJudgementApplicationService.findByPhase(phaseEntity).stream().findFirst().ifPresent(
                    judgement -> labelSet.addAll(judgement.getInstance().getLabelSet()));
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_CHOICE.name())) {
            this.sentimentAndChoiceJudgementApplicationService.findByPhase(phaseEntity).stream().findFirst().ifPresent(
                    judgement -> labelSet.addAll(judgement.getInstance().getLabelSet()));
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SPAN.name())) {
            this.sentimentAndChoiceJudgementApplicationService.findByPhase(phaseEntity).stream().findFirst().ifPresent(
                    judgement -> labelSet.addAll(judgement.getInstance().getLabelSet()));
        }

        return labelSet;
    }





}
