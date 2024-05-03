package de.garrafao.phitag.domain.statistic.statistictask;

import java.util.List;
import java.util.Optional;

import de.garrafao.phitag.domain.status.Status;
import de.garrafao.phitag.domain.user.User;

public interface StatisticTaskRepository {

    StatisticTask save(final StatisticTask task);

    List<StatisticTask> findByBotAndStatus(final User user, final Status status);

    Optional<StatisticTask> findFirst1ByBotAndStatus(final User user, final Status status);

    Optional<StatisticTask> findById(final Integer id);
}
