package de.garrafao.phitag.infrastructure.persistence.jpa.usecase;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.usecase.Usecase;
import de.garrafao.phitag.domain.usecase.UsecaseRepository;

@Repository
public class UsecaseRepositoryBridge implements UsecaseRepository {

    private final UsecaseRepositoryJpa usecaseRepositoryJpa;

    @Autowired
    public UsecaseRepositoryBridge(UsecaseRepositoryJpa usecaseRepositoryJpa) {
        this.usecaseRepositoryJpa = usecaseRepositoryJpa;
    }

    @Override
    public List<Usecase> findAll() {
        return this.usecaseRepositoryJpa.findAll();
    }

    @Override
    public Optional<Usecase> findByName(final String name) {
        return this.usecaseRepositoryJpa.findByName(name);
    }

    
    
}
