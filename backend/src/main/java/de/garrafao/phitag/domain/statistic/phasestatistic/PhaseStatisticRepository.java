package de.garrafao.phitag.domain.statistic.phasestatistic;

import java.util.Optional;

public interface PhaseStatisticRepository {

    public Optional<PhaseStatistic> findByOwnernameAndProjectnameAndPhasename(final String ownername,
            final String projectname, final String phasename);

    public PhaseStatistic save(final PhaseStatistic phaseStatistic);
    
}
