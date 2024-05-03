package de.garrafao.phitag.domain.statistic.statisticannotationmeasure;

import java.util.Optional;
import java.util.Set;

public interface StatisticAnnotationMeasureRepository {

    Set<StatisticAnnotationMeasure> findAll();

    Optional<StatisticAnnotationMeasure> findById(final String id);

    Optional<StatisticAnnotationMeasure> findByName(final String name);

}