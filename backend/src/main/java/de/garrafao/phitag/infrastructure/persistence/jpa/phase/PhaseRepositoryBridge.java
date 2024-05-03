package de.garrafao.phitag.infrastructure.persistence.jpa.phase;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.phase.PhaseRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.phase.query.PhaseQueryJpa;

@Repository
public class PhaseRepositoryBridge implements PhaseRepository {

    private final PhaseRepositoryJpa phaseRepositoryJpa;

    @Autowired
    public PhaseRepositoryBridge(PhaseRepositoryJpa phaseRepositoryJpa) {
        this.phaseRepositoryJpa = phaseRepositoryJpa;
    }

    @Override
    public List<Phase> findByQuery(Query query) {
        PhaseQueryJpa phaseQueryJpa = new PhaseQueryJpa(query);

        return phaseRepositoryJpa.findAll(phaseQueryJpa);
    }

    @Override
    public Page<Phase> findByQueryPaged(Query query, PageRequestWraper page) {
        PhaseQueryJpa phaseQueryJpa = new PhaseQueryJpa(query);

        return phaseRepositoryJpa.findAll(phaseQueryJpa, page.getPageRequest());
    }

    @Override
    public Optional<Phase> findByIdNameAndIdProjectidNameAndIdProjectidOwnername(String name, String projectname, String ownername) {
        return phaseRepositoryJpa.findByIdNameAndIdProjectidNameAndIdProjectidOwnername(name, projectname, ownername);
    }

    @Override
    public Phase save(Phase phase) {
        return this.phaseRepositoryJpa.save(phase);
    }

    @Override
    public void delete(final Phase phase) {
        this.phaseRepositoryJpa.delete(phase);
    }

}
