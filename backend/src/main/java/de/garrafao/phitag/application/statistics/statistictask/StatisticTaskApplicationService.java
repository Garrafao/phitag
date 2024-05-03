package de.garrafao.phitag.application.statistics.statistictask;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.statistics.statistictask.data.dto.StatisticTaskDto;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.project.Project;
import de.garrafao.phitag.domain.statistic.error.StatisticException;
import de.garrafao.phitag.domain.statistic.statistictask.StatisticTask;
import de.garrafao.phitag.domain.statistic.statistictask.StatisticTaskRepository;
import de.garrafao.phitag.domain.status.Status;
import de.garrafao.phitag.domain.status.TaskStatusEnum;
import de.garrafao.phitag.domain.user.User;

@Service
public class StatisticTaskApplicationService {

    private final StatisticTaskRepository statisticTaskRepository;

    // Common Service

    private final CommonService commonService;

    private final ValidationService validationService;

    @Autowired
    public StatisticTaskApplicationService(
            final StatisticTaskRepository statisticTaskRepository,

            @Lazy final CommonService commonService,
            @Lazy final ValidationService validationService) {
        this.statisticTaskRepository = statisticTaskRepository;

        this.commonService = commonService;
        this.validationService = validationService;
    }

    // API

    // Getter

    /**
     * Returns the next pendign statisic task for the given statistic bot.
     * 
     * @param authenticationToken the authentication token
     * @return the next pending statistic task as a {@link StatisticTaskDto}
     * 
     */
    public StatisticTaskDto getNextPendingStatisticTask(final String authenticationToken) {
        final User statisticBot = this.commonService.getUserByAuthenticationToken(authenticationToken);

        this.validationService.botAccess(statisticBot);

        final Status status = this.commonService.getStatus(TaskStatusEnum.TASK_PENDING.name());

        return StatisticTaskDto.from(
                this.statisticTaskRepository.findFirst1ByBotAndStatus(statisticBot, status).orElse(null));

    }

    public StatisticTask getStatisticTask(final Integer statisticTaskId) {
        return this.statisticTaskRepository.findById(statisticTaskId).orElseThrow(() -> new StatisticException(
                "Statistic task with id " + statisticTaskId + " does not exist."));
    }

    // Setter

    /**
     * Sets the status of the statistic task to {@link TaskStatusEnum#TASK_DONE}.
     * 
     * @param authenticationToken the authentication token
     * @param statisticTaskId     the statistic task id
     * @param status              the status
     * 
     */
    @Transactional
    public void setStatisticTaskStatus(final String authenticationToken, final Integer statisticTaskId,
            final String status) {
        final User statisticBot = this.commonService.getUserByAuthenticationToken(authenticationToken);

        this.validationService.botAccess(statisticBot);

        final Status statusEntity = this.commonService.getStatus(status);

        final Optional<StatisticTask> statisticTask = this.statisticTaskRepository.findById(statisticTaskId);

        if (statisticTask.isPresent()) {
            statisticTask.get().setStatus(statusEntity);
            this.statisticTaskRepository.save(statisticTask.get());
        }
    }

    // Utility/Local

    /**
     * Creates a new statistic task for a user.
     * 
     * @param statisticBot the statistic bot
     * @param user         the user
     * 
     * @return the created statistic task
     */
    @Transactional
    public StatisticTask createStatisticTask(final User statisticBot, final User user) {
        final Status status = this.commonService.getStatus(TaskStatusEnum.TASK_PENDING.name());

        final StatisticTask statisticTask = new StatisticTask(statisticBot, status, user.getUsername());

        return this.statisticTaskRepository.save(statisticTask);
    }

    /**
     * Creates a new statistic task for a project.
     * 
     * @param statisticBot the statistic bot
     * @param project      the project
     * 
     * @return the created statistic task
     */
    @Transactional
    public StatisticTask createStatisticTask(final User statisticBot, final Project project) {
        final Status status = this.commonService.getStatus(TaskStatusEnum.TASK_PENDING.name());

        final StatisticTask statisticTask = new StatisticTask(statisticBot, status, project.getId().getName(),
                project.getId().getOwnername());

        return this.statisticTaskRepository.save(statisticTask);
    }

    /**
     * Creates a new statistic task for a phase.
     * 
     * @param statisticBot the statistic bot
     * @param phase        the phase
     * 
     * @return the created statistic task
     */
    @Transactional
    public StatisticTask createStatisticTask(final User statisticBot, final Phase phase) {
        final Status status = this.commonService.getStatus(TaskStatusEnum.TASK_PENDING.name());

        final StatisticTask statisticTask = new StatisticTask(statisticBot, status, phase.getId().getName(),
                phase.getId().getProjectid().getName(), phase.getId().getProjectid().getOwnername());

        return this.statisticTaskRepository.save(statisticTask);
    }

    /**
     * Creates a new statistic task for a phase.
     * 
     * @param statisticBot the statistic bot
     * @param annotator    the annotator
     * 
     * @return the created statistic task
     */
    @Transactional
    public StatisticTask createStatisticTask(final User statisticBot, final Annotator annotator) {
        final Status status = this.commonService.getStatus(TaskStatusEnum.TASK_PENDING.name());

        final StatisticTask statisticTask = new StatisticTask(statisticBot, status,
                "",
                annotator.getId().getProjectid().getName(),
                annotator.getId().getProjectid().getOwnername(),
                annotator.getId().getUsername());

        return this.statisticTaskRepository.save(statisticTask);
    }

}
