package de.garrafao.phitag.infrastructure.persistence.jpa.statistic.statistictask;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.statistic.statistictask.StatisticTask;
import de.garrafao.phitag.domain.statistic.statistictask.StatisticTaskRepository;
import de.garrafao.phitag.domain.status.Status;
import de.garrafao.phitag.domain.user.User;

@Repository
public class StatisticTaskRepositoryBridge implements StatisticTaskRepository {

    private final StatisticTaskRepositoryJpa statisticTaskRepositoryJpa;

    @Autowired
    public StatisticTaskRepositoryBridge(final StatisticTaskRepositoryJpa statisticTaskRepositoryJpa) {
        this.statisticTaskRepositoryJpa = statisticTaskRepositoryJpa;
    }

    @Override
    public StatisticTask save(final StatisticTask task) {
        return this.statisticTaskRepositoryJpa.save(task);
    }

    @Override
    public List<StatisticTask> findByBotAndStatus(final User user, final Status status) {
        return this.statisticTaskRepositoryJpa.findByBotAndStatus(user, status);
    }

    @Override
    public Optional<StatisticTask> findFirst1ByBotAndStatus(final User user, final Status status) {
        return this.statisticTaskRepositoryJpa.findFirst1ByBotAndStatus(user, status);
    }

    @Override
    public Optional<StatisticTask> findById(final Integer id) {
        return this.statisticTaskRepositoryJpa.findById(id);
    }

    

}
