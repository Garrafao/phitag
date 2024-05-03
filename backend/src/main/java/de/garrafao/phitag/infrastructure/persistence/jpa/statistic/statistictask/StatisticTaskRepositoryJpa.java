package de.garrafao.phitag.infrastructure.persistence.jpa.statistic.statistictask;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.garrafao.phitag.domain.statistic.statistictask.StatisticTask;
import de.garrafao.phitag.domain.status.Status;
import de.garrafao.phitag.domain.user.User;

public interface StatisticTaskRepositoryJpa extends JpaRepository<StatisticTask, Integer> {

    List<StatisticTask> findByBotAndStatus(final User user, final Status status);

    Optional<StatisticTask> findFirst1ByBotAndStatus(final User user, final Status status);

    Optional<StatisticTask> findById(final Integer id);
    
}
