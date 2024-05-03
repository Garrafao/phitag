package de.garrafao.phitag.infrastructure.persistence.jpa.status;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.status.Status;
import de.garrafao.phitag.domain.status.StatusRepository;

@Repository
public class StatusRepositoryBridge implements StatusRepository {

    private final StatusRepositoryJpa statusRepositoryJpa;

    @Autowired
    public StatusRepositoryBridge(StatusRepositoryJpa statusRepositoryJpa) {
        this.statusRepositoryJpa = statusRepositoryJpa;
    }


    @Override
    public List<Status> findAll() {
        return statusRepositoryJpa.findAll();
    }

    @Override
    public Optional<Status> findByName(String name) {
        return statusRepositoryJpa.findByName(name);
    }
    
}
