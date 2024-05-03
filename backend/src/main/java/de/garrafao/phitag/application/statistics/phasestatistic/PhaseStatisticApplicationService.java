package de.garrafao.phitag.application.statistics.phasestatistic;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.statistics.phasestatistic.data.command.UpdatePhaseStatisticKrippendorffCommand;
import de.garrafao.phitag.application.statistics.phasestatistic.data.dto.PhaseStatisticDto;
import de.garrafao.phitag.application.statistics.statistictask.StatisticTaskApplicationService;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.statistic.error.StatisticException;
import de.garrafao.phitag.domain.statistic.phasestatistic.PhaseStatistic;
import de.garrafao.phitag.domain.statistic.phasestatistic.PhaseStatisticRepository;
import de.garrafao.phitag.domain.statistic.statistictask.StatisticTask;
import de.garrafao.phitag.domain.status.TaskStatusEnum;
import de.garrafao.phitag.domain.user.User;

@Service
public class PhaseStatisticApplicationService {

    // Repository

    private final PhaseStatisticRepository phaseStatisticRepository;

    private final StatisticTaskApplicationService statisticTaskApplicationService;

    // Lazy Loaded Services

    private final CommonService commonService;

    private final ValidationService validationService;

    @Autowired
    public PhaseStatisticApplicationService(
            final PhaseStatisticRepository phaseStatisticRepository,
            final StatisticTaskApplicationService statisticTaskApplicationService,

            @Lazy final CommonService commonService,
            @Lazy final ValidationService validationService) {
        this.phaseStatisticRepository = phaseStatisticRepository;
        this.statisticTaskApplicationService = statisticTaskApplicationService;

        this.commonService = commonService;
        this.validationService = validationService;
    }

    // API methods

    // Getter

    /**
     * Returns the phase statistic for the given phase.
     * 
     * @param authenticationToken the authentication token
     * @param ownername           the ownername
     * @param projectname         the projectname
     * @param phasename           the phasename
     * 
     * @return the phase statistic as a {@link PhaseStatisticDto}
     */
    public PhaseStatisticDto getPhaseStatistic(
            final String authenticationToken,
            final String ownername,
            final String projectname,
            final String phasename) {
        final Phase phase = this.commonService.getPhase(ownername, projectname, phasename);
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);

        this.validationService.projectAccess(requester, phase.getProject());

        final PhaseStatistic phaseStatistic = this.phaseStatisticRepository
                .findByOwnernameAndProjectnameAndPhasename(ownername, projectname, phasename)
                .orElseThrow(() -> new IllegalStateException("PhaseStatistic not found"));

        return PhaseStatisticDto.from(phaseStatistic);
    }

    /**
     * Get the phase annotations for the given phase.
     * 
     * @param authenticationToken the authentication token
     * @param phase               the phase
     * @return the phase annotations
     */

    // Setter

    /**
     * Creates a new phase statistic for the given phase.
     * 
     * @param phase the phase
     */
    @Transactional
    public void initializePhaseStatistic(final Phase phase) {
        final PhaseStatistic phaseStatistic = new PhaseStatistic(
                phase.getId().getProjectid().getOwnername(),
                phase.getId().getProjectid().getName(),
                phase.getId().getName());
        this.phaseStatisticRepository.save(phaseStatistic);
    }

    /**
     * Updates Krippendorff's alpha for the given phase.
     * 
     * @param authenticationToken the authentication token
     * @param command             the command
     */
    @Transactional
    public void updatePhaseStatisticKrippendorff(
            final String authenticationToken,
            final UpdatePhaseStatisticKrippendorffCommand command) {
        this.commonService.getPhase(
                command.getOwnername(),
                command.getProjectname(),
                command.getPhasename());
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

        final PhaseStatistic phaseStatistic = this.phaseStatisticRepository
                .findByOwnernameAndProjectnameAndPhasename(
                        command.getOwnername(),
                        command.getProjectname(),
                        command.getPhasename())
                .orElseThrow(() -> new StatisticException("PhaseStatistic not found"));

        phaseStatistic.setKrippendorffalpha(command.getKrippendorffalpha());
        phaseStatistic.setInterannotatorKrippendorffalpha(command.getInterannotatorKrippendorffalpha());

        this.phaseStatisticRepository.save(phaseStatistic);

        // finalize statistic task
        this.statisticTaskApplicationService.setStatisticTaskStatus(authenticationToken, command.getStatisticTaskId(),
                TaskStatusEnum.TASK_COMPLETED.name());
    }

    // Helper methods

    /**
     * Updates the phase statistic for the given phase for an annotation.
     * 
     * @param annotator the annotator who created the annotation
     * @param phase     the phase
     */
    @Transactional
    public void updatePhaseStatisticForAnnotation(final Annotator annotator, final Phase phase) {
        final PhaseStatistic phaseStatistic = this.phaseStatisticRepository
                .findByOwnernameAndProjectnameAndPhasename(
                        phase.getId().getProjectid().getOwnername(),
                        phase.getId().getProjectid().getName(),
                        phase.getId().getName())
                .orElseThrow(() -> new StatisticException("PhaseStatistic not found"));

        phaseStatistic.setAnnotations(phaseStatistic.getAnnotations() + 1);

        Map<String, Integer> annotatorAnnotationCountMap = phaseStatistic.getAnnotatorAnnotationCountMap();
        annotatorAnnotationCountMap.put(annotator.getId().getUsername(),
                annotatorAnnotationCountMap.getOrDefault(annotator.getId().getUsername(), 0) + 1);

        this.phaseStatisticRepository.save(phaseStatistic);
    }

    /**
     * Create a new phase statistic task for the given phase.
     * Tasks created by this method will be executed by the phase statistic bot
     * sometime in the future in a distributed manner.
     * 
     * Tasks created:
     * - Krippendorff's alpha
     * 
     * @param phase the phase
     */
    @Transactional
    public void createPhaseStatisticTask(final Phase phase) {
        // create task for Krippendorff's alpha
        final User phaseStatisticBot = this.commonService.getUser("Statistic-Bot-Krippendorff-Phase");

        this.statisticTaskApplicationService.createStatisticTask(phaseStatisticBot, phase);
    }

}
