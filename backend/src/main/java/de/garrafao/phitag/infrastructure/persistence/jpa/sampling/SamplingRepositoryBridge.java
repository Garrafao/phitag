package de.garrafao.phitag.infrastructure.persistence.jpa.sampling;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.sampling.Sampling;
import de.garrafao.phitag.domain.sampling.SamplingRepository;

@Repository
public class SamplingRepositoryBridge implements SamplingRepository {

    private final SamplingRepositoryJpa samplingRepositoryJpa;
    
    @Autowired
    public SamplingRepositoryBridge(SamplingRepositoryJpa samplingRepositoryJpa) {
        this.samplingRepositoryJpa = samplingRepositoryJpa;
    }

    @Override
    public List<Sampling> findAll() {
        return samplingRepositoryJpa.findAll();
    }

    @Override
    public Optional<Sampling> findByName(String name) {
        return samplingRepositoryJpa.findByName(name);
    }

    

}
