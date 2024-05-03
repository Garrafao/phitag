package de.garrafao.phitag.application.statistics.annotatostatistic;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.statistics.annotatostatistic.data.command.UpdateAnnotatorStatisticKrippendorffCommand;
import de.garrafao.phitag.application.statistics.annotatostatistic.data.dto.AnnotatorStatisticDto;
import de.garrafao.phitag.application.statistics.statistictask.StatisticTaskApplicationService;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.phitagdata.usage.UsageRepository;
import de.garrafao.phitag.domain.project.Project;
import de.garrafao.phitag.domain.statistic.annotatorstatistic.AnnotatorStatistic;
import de.garrafao.phitag.domain.statistic.annotatorstatistic.AnnotatorStatisticRepository;
import de.garrafao.phitag.domain.statistic.error.StatisticException;
import de.garrafao.phitag.domain.statistic.statistictask.StatisticTask;
import de.garrafao.phitag.domain.status.TaskStatusEnum;
import de.garrafao.phitag.domain.user.User;

@Service
public class AnnotatorStatisticApplicationService {

    // Repository

    private final AnnotatorStatisticRepository annotatorStatisticRepository;

    private final UsageRepository usageRepository;

    // Repository

    private final StatisticTaskApplicationService statisticTaskApplicationService;

    // Lazy loading

    private final CommonService commonService;

    private final ValidationService validationService;

    @Autowired
    public AnnotatorStatisticApplicationService(
            final AnnotatorStatisticRepository annotatorStatisticRepository,
            final UsageRepository usageRepository,

            final StatisticTaskApplicationService statisticTaskApplicationService,

            @Lazy final CommonService commonService,
            @Lazy final ValidationService validationService) {
        this.annotatorStatisticRepository = annotatorStatisticRepository;
        this.usageRepository = usageRepository;

        this.statisticTaskApplicationService = statisticTaskApplicationService;

        // lazy loading
        this.commonService = commonService;
        this.validationService = validationService;
    }

    // API methods

    // Getter

    /**
     * Returns the annotator statistic for the given annotator.
     * 
     * @param authenticationToken the authentication token
     * @param annotatorname       the username of the annotator
     * @param ownername           the name of the owner of the project
     * @param projectname         the projectname of the project
     * @return the annotator statistic as a {@link AnnotatorStatisticDto}
     */
    public AnnotatorStatisticDto getAnnotatorStatistic(
            final String authenticationToken,
            final String annotatorname,
            final String ownername,
            final String projectname) {
        final Project project = this.commonService.getProject(ownername, projectname);
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);

        this.validationService.projectAccess(requester, project);

        final AnnotatorStatistic annotatorStatistic = this.annotatorStatisticRepository
                .findByAnnotatornameAndOwnernameAndProjectname(annotatorname, ownername, projectname)
                .orElseThrow(() -> new StatisticException("Annotator statistic not found"));

        return AnnotatorStatisticDto.from(annotatorStatistic);
    }

    // Setter

    /**
     * Creates a new annotator statistic for the given annotator.
     * 
     * @param annotator the annotator
     */
    @Transactional
    public void initializeAnnotatorStatistic(final Annotator annotator) {
        final AnnotatorStatistic annotatorStatistic = new AnnotatorStatistic(
                annotator.getId().getUsername(),
                annotator.getId().getProjectid().getOwnername(),
                annotator.getId().getProjectid().getName());
        this.annotatorStatisticRepository.save(annotatorStatistic);
    }

    /**
     * Updates Krippendorff's alpha for the given annotator.
     * 
     * @param authenticationToken the authentication token
     * @param command             the command
     */
    @Transactional
    public void updateAnnotatorStatisticKrippendorff(
            final String authenticationToken,
            final UpdateAnnotatorStatisticKrippendorffCommand command) {

        this.commonService.getAnnotator(command.getOwnername(), command.getProjectname(), command.getAnnotatorname());
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);

        this.validationService.botAccess(requester);

        final StatisticTask statisticTask = this.statisticTaskApplicationService
                .getStatisticTask(command.getStatisticTaskId());

        if (!requester.getUsername().equals(statisticTask.getBot().getUsername())) {
            throw new StatisticException("Only the corresponding statistic bot can update Krippendorff's alpha");
        }

        if (statisticTask.getStatus().getName().equals(TaskStatusEnum.TASK_COMPLETED.name())) {
            throw new StatisticException("Statistic task is already completed");
        }

        final AnnotatorStatistic annotatorStatistic = this.annotatorStatisticRepository
                .findByAnnotatornameAndOwnernameAndProjectname(
                        command.getAnnotatorname(), command.getOwnername(), command.getProjectname())
                .orElseThrow(() -> new StatisticException("AnnotatorStatistic not found"));

        annotatorStatistic.setKrippendorffalpha(command.getKrippendorffalpha());
        annotatorStatistic.setInterannotatorKrippendorffalpha(command.getInterannotatorKrippendorffalpha());

        this.annotatorStatisticRepository.save(annotatorStatistic);

        // finalize statistic task
        this.statisticTaskApplicationService.setStatisticTaskStatus(authenticationToken, command.getStatisticTaskId(),
                TaskStatusEnum.TASK_COMPLETED.name());
    }

    // Helper

    /**
     * Update the annotator statistic for an annotation.
     * 
     * @param annotator the annotator
     * @param phase     the phase of the annotation
     */
    @Transactional
    public void updateAnnotationStatistic(final Annotator annotator, final Phase phase) {
        // get annotator statistic
        final AnnotatorStatistic annotatorStatistic = this.annotatorStatisticRepository
                .findByAnnotatornameAndOwnernameAndProjectname(
                        annotator.getId().getUsername(),
                        annotator.getId().getProjectid().getOwnername(),
                        annotator.getId().getProjectid().getName())
                .orElseThrow(() -> new StatisticException("Annotator statistic not found"));

        // update annotation count
        annotatorStatistic.setAnnotations(annotatorStatistic.getAnnotations() + 1);

        Map<String, Integer> phaseCount = annotatorStatistic.getPhaseAnnotationCountMap();
        phaseCount.put(phase.getId().getName(), phaseCount.getOrDefault(phase.getId().getName(), 0) + 1);

        // save
        this.annotatorStatisticRepository.save(annotatorStatistic);
    }


    /**
     * Create a new annotator statistic task for the given annotator.
     * Tasks created by this method will be executed by the annotator statistic bot
     * sometime in the future in a distributed manner.
     * 
     * Tasks created:
     * - Krippendorff's alpha
     * 
     * @param annotator the annotator
     */
    @Transactional
    public void createAnnotatorStatisticTask(final Annotator annotator) {
        // create task for Krippendorff's alpha
        final User phaseStatisticBot = this.commonService.getUser("Statistic-Bot-Krippendorff-Annotator");

        this.statisticTaskApplicationService.createStatisticTask(phaseStatisticBot, annotator);
    }


}
