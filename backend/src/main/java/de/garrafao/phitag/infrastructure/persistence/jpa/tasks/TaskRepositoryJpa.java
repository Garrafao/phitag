package de.garrafao.phitag.infrastructure.persistence.jpa.tasks;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.status.Status;
import de.garrafao.phitag.domain.tasks.Task;
import de.garrafao.phitag.domain.user.User;

public interface TaskRepositoryJpa extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {

    public Optional<Task> findById(final Integer id);

    public List<Task> findByBotAndStatus(final User user, final Status status);

    public List<Task> findByPhase(final Phase phase);

}
