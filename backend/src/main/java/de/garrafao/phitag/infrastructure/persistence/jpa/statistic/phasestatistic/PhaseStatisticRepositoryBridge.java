package de.garrafao.phitag.infrastructure.persistence.jpa.statistic.phasestatistic;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.statistic.phasestatistic.PhaseStatistic;
import de.garrafao.phitag.domain.statistic.phasestatistic.PhaseStatisticRepository;

@Repository
public class PhaseStatisticRepositoryBridge implements PhaseStatisticRepository {

    private final PhaseStatisticRepositoryJpa phaseStatisticRepositoryJpa;

    @Autowired
    public PhaseStatisticRepositoryBridge(final PhaseStatisticRepositoryJpa phaseStatisticRepositoryJpa) {
        this.phaseStatisticRepositoryJpa = phaseStatisticRepositoryJpa;
    }

    @Override
    public Optional<PhaseStatistic> findByOwnernameAndProjectnameAndPhasename(String ownername, String projectname,
            String phasename) {
        return this.phaseStatisticRepositoryJpa.findByOwnernameAndProjectnameAndPhasename(ownername, projectname,
                phasename);
    }

    @Override
    public PhaseStatistic save(PhaseStatistic phaseStatistic) {
        return this.phaseStatisticRepositoryJpa.save(phaseStatistic);
    }

}
