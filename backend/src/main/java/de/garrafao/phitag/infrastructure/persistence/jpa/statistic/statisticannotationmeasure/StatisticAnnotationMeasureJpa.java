package de.garrafao.phitag.infrastructure.persistence.jpa.statistic.statisticannotationmeasure;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import de.garrafao.phitag.domain.statistic.statisticannotationmeasure.StatisticAnnotationMeasure;

public interface StatisticAnnotationMeasureJpa extends JpaRepository<StatisticAnnotationMeasure, String> {


    Optional<StatisticAnnotationMeasure> findById(final String id);

    Optional<StatisticAnnotationMeasure> findByName(final String name);


}
