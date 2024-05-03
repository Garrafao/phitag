package de.garrafao.phitag.infrastructure.persistence.jpa.tasks;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.status.Status;
import de.garrafao.phitag.domain.tasks.Task;
import de.garrafao.phitag.domain.tasks.TaskRepository;
import de.garrafao.phitag.domain.user.User;
import de.garrafao.phitag.infrastructure.persistence.jpa.tasks.query.TaskQueryJpa;

@Repository
public class TaskRepositoryBridge implements TaskRepository {
    
    private final TaskRepositoryJpa taskRepositoryJpa;

    @Autowired
    public TaskRepositoryBridge(TaskRepositoryJpa taskRepositoryJpa) {
        this.taskRepositoryJpa = taskRepositoryJpa;
    }

    @Override
    public Task save(final Task task) {
        return taskRepositoryJpa.save(task);
    }

    @Override
    public Optional<Task> findById(final Integer id) {
        return taskRepositoryJpa.findById(id);
    }

    @Override
    public List<Task> findByBotAndStatus(final User user, final Status status) {
        return taskRepositoryJpa.findByBotAndStatus(user, status);
    }

    @Override
    public List<Task> findByPhase(final Phase phase) {
        return taskRepositoryJpa.findByPhase(phase);
    }

    @Override
    public List<Task> findByQuery(Query query) {
        return taskRepositoryJpa.findAll(new TaskQueryJpa(query));
    }

}
