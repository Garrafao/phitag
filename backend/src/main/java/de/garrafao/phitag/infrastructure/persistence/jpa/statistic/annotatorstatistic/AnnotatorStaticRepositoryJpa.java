package de.garrafao.phitag.infrastructure.persistence.jpa.statistic.annotatorstatistic;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.garrafao.phitag.domain.statistic.annotatorstatistic.AnnotatorStatistic;
import de.garrafao.phitag.domain.statistic.annotatorstatistic.AnnotatorStatisticId;

public interface AnnotatorStaticRepositoryJpa extends JpaRepository<AnnotatorStatistic, AnnotatorStatisticId> {

    Optional<AnnotatorStatistic> findByAnnotatornameAndOwnernameAndProjectname(final String annotatorname,
            final String ownername, final String projectname);
}
