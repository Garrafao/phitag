package de.garrafao.phitag.domain.tasks;

import java.util.List;
import java.util.Optional;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.status.Status;
import de.garrafao.phitag.domain.user.User;

public interface TaskRepository {

    Task save(final Task task);

    Optional<Task> findById(final Integer id);

    List<Task> findByBotAndStatus(final User user, final Status status);

    List<Task> findByPhase(final Phase phase);

    List<Task> findByQuery(final Query query);
}
