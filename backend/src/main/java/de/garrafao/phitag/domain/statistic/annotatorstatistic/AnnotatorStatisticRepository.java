package de.garrafao.phitag.domain.statistic.annotatorstatistic;

import java.util.Optional;

public interface AnnotatorStatisticRepository {

    public Optional<AnnotatorStatistic> findByAnnotatornameAndOwnernameAndProjectname(final String annotatorname,
            final String ownername, final String projectname);

    public AnnotatorStatistic save(final AnnotatorStatistic annotatorStatistic);

}
