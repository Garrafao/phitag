package de.garrafao.phitag.application.statistics.deprecated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.application.annotator.data.dto.AnnotatorDto;
import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.judgement.JudgementApplicationService;
import de.garrafao.phitag.application.statistics.deprecated.data.AnnotatorStatisticDto;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.annotator.AnnotatorRepository;
import de.garrafao.phitag.domain.annotator.query.AnnotatorQueryBuilder;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.phase.PhaseRepository;
import de.garrafao.phitag.domain.phase.query.PhaseQueryBuilder;
import de.garrafao.phitag.domain.project.Project;
import de.garrafao.phitag.domain.user.User;

@Deprecated
@Service
public class StatisticApplicationService {

    // Repository

    private final PhaseRepository phaseRepository;

    private final AnnotatorRepository annotatorRepository;

    // Services

    private final CommonService commonService;

    private final ValidationService validationService;

    private final JudgementApplicationService judgementApplicationService;

    @Autowired
    public StatisticApplicationService(
            final PhaseRepository phaseRepository,
            final AnnotatorRepository annotatorRepository,
            final CommonService commonService,
            final ValidationService validationService,
            final JudgementApplicationService judgementApplicationService) {
        this.phaseRepository = phaseRepository;
        this.annotatorRepository = annotatorRepository;

        this.commonService = commonService;
        this.validationService = validationService;
        this.judgementApplicationService = judgementApplicationService;
    }

    // API methods

    // Getter

    /**
     * Returns the statistics of the annotators of the given project.
     * 
     * Note: This is a very expensive operation and currently mostly used for
     * testing and demonstration purposes.
     * 
     * TODO: Find a better way to count the annotations and maybe cache the result
     * TODO: Extend
     * 
     * @param authenticationToken the authentication token of the user
     * @param owner               the owner of the project
     * @param project             the name of the project
     * @return {@link List} of {@link AnnotatorStatisticDto} of the annotators
     */
    public List<AnnotatorStatisticDto> getProjectStatistic(final String authenticationToken, final String owner,
            final String project) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Project projectEntity = this.commonService.getProject(owner, project);

        this.validationService.projectAccess(requester, projectEntity);

        // Calculate statistics
        List<AnnotatorStatisticDto> annotatorStatistics = new ArrayList<>();
        List<Phase> phases = this.phaseRepository
                .findByQuery(new PhaseQueryBuilder().withOwner(owner).withProject(project).build());
        List<Annotator> annotators = this.annotatorRepository
                .findByQuery(new AnnotatorQueryBuilder().withOwner(owner).withProject(project).build());

        for (Annotator annotator : annotators) {
            int numberOfAnnotations = getNumberOfAnnotationsForAnnotator(annotator);
            Map<Phase, Integer> numberOfAnnotationsPerPhase = getNumberOfAnnotationsPerPhaseForAnnotator(annotator,
                    phases);

            annotatorStatistics.add(AnnotatorStatisticDto.of(AnnotatorDto.from(annotator), numberOfAnnotations,
                    numberOfAnnotationsPerPhase));
        }

        return annotatorStatistics;
    }

    /**
     * Returns the statistics of the annotators of the given projects phase.
     * 
     * Note: This is a very expensive operation and currently mostly used for
     * testing and demonstration purposes.
     * 
     * TODO: Find a better way to count the annotations and maybe cache the result
     * TODO: Extend
     * 
     * @param authenticationToken the authentication token of the user
     * @param owner               the owner of the project
     * @param project             the name of the project
     * @param phase               the name of the phase
     * @return {@link List} of {@link AnnotatorStatisticDto} of the annotators
     */
    public List<AnnotatorStatisticDto> getPhaseStatistic(final String authenticationToken, final String owner,
            final String project, final String phase) {

        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Project projectEntity = this.commonService.getProject(owner, project);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        this.validationService.projectAccess(requester, projectEntity);

        // Calculate statistics
        List<AnnotatorStatisticDto> annotatorStatistics = new ArrayList<>();
        List<Annotator> annotators = this.annotatorRepository
                .findByQuery(new AnnotatorQueryBuilder().withOwner(owner).withProject(project).build());

        for (Annotator annotator : annotators) {
            int numberOfAnnotations = countJudgements(annotator, phaseEntity);
            Map<Phase, Integer> numberOfAnnotationsPerPhase = new HashMap<>();
            numberOfAnnotationsPerPhase.put(phaseEntity, numberOfAnnotations);

            annotatorStatistics.add(AnnotatorStatisticDto.of(AnnotatorDto.from(annotator), numberOfAnnotations,
                    numberOfAnnotationsPerPhase));
        }

        return annotatorStatistics;
    }

    /**
     * Returns the number of annotations for the given annotator.
     * 
     * Note: This is a very expensive operation and currently mostly used for
     * testing and demonstration purposes.
     * 
     * TODO: Find a better way to count the annotations and maybe cache the result
     * TODO: Extend
     * 
     * @param authenticationToken the authentication token of the user
     * @param owner               the owner of the project
     * @param project             the name of the project
     * @param annotator           the name of the annotator
     * @return the number of annotations as {@link AnnotationStatisticDto}
     */
    public AnnotatorStatisticDto getAnnotatorStatistic(final String authenticationToken, final String owner,
            final String project, final String annotator) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Project projectEntity = this.commonService.getProject(owner, project);
        final Annotator annotatorEntity = this.commonService.getAnnotator(owner, project, annotator);

        this.validationService.projectAccess(requester, projectEntity);

        // Calculate statistics
        int numberOfAnnotations = getNumberOfAnnotationsForAnnotator(annotatorEntity);
        List<Phase> phases = this.phaseRepository
                .findByQuery(new PhaseQueryBuilder().withOwner(owner).withProject(project).build());

        Map<Phase, Integer> numberOfAnnotationsPerPhase = getNumberOfAnnotationsPerPhaseForAnnotator(annotatorEntity,
                phases);
        return AnnotatorStatisticDto.of(AnnotatorDto.from(annotatorEntity), numberOfAnnotations,
                numberOfAnnotationsPerPhase);
    }

    // Helper

    private Map<Phase, Integer> getNumberOfAnnotationsPerPhaseForAnnotator(final Annotator annotator,
            final List<Phase> phases) {
        Map<Phase, Integer> numberOfAnnotationsPerPhase = new HashMap<>();

        for (Phase phase : phases) {
            if (!phase.isTutorial()) {
                int numberOfAnnotations = countJudgements(annotator, phase);
                numberOfAnnotationsPerPhase.put(phase, numberOfAnnotations);
            }
        }

        return numberOfAnnotationsPerPhase;
    }

    private int countJudgements(Annotator annotator, Phase phase) {
        if (phase.isTutorial()) {
            return 0;
        }
        return judgementApplicationService.countJudgements(annotator, phase);
    }

    private int getNumberOfAnnotationsForAnnotator(final Annotator annotator) {
        return judgementApplicationService.countJudgements(annotator);
    }

}
