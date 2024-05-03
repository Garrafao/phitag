package de.garrafao.phitag.infrastructure.persistence.jpa.visibility;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.visibility.Visibility;
import de.garrafao.phitag.domain.visibility.VisibilityRepository;

@Repository
public class VisibilityRepositoryBridge implements VisibilityRepository {

    private final VisibilityRepositoryJpa repository;

    @Autowired
    public VisibilityRepositoryBridge(VisibilityRepositoryJpa repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Visibility> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Set<Visibility> findAll() {
        return repository.findAll().stream().collect(Collectors.toSet());
    }
    
}
