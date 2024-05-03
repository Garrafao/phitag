package de.garrafao.phitag.infrastructure.persistence.jpa.statistic.phasestatistic;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.garrafao.phitag.domain.statistic.phasestatistic.PhaseStatistic;
import de.garrafao.phitag.domain.statistic.phasestatistic.PhaseStatisticId;

public interface PhaseStatisticRepositoryJpa extends JpaRepository<PhaseStatistic, PhaseStatisticId> {

    public Optional<PhaseStatistic> findByOwnernameAndProjectnameAndPhasename(final String ownername,
            final String projectname, final String phasename);

}
