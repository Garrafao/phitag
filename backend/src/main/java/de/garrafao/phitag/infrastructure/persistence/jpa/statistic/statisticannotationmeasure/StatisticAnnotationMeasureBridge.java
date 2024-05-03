package de.garrafao.phitag.infrastructure.persistence.jpa.statistic.statisticannotationmeasure;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.statistic.statisticannotationmeasure.StatisticAnnotationMeasure;
import de.garrafao.phitag.domain.statistic.statisticannotationmeasure.StatisticAnnotationMeasureRepository;

@Repository
public class StatisticAnnotationMeasureBridge implements StatisticAnnotationMeasureRepository {

    private final StatisticAnnotationMeasureJpa statisticAnnotationMeasureJpa;

    @Autowired
    public StatisticAnnotationMeasureBridge(final StatisticAnnotationMeasureJpa statisticAnnotationMeasureJpa) {
        this.statisticAnnotationMeasureJpa = statisticAnnotationMeasureJpa;
    }

    @Override
    public Set<StatisticAnnotationMeasure> findAll() {
        return this.statisticAnnotationMeasureJpa.findAll().stream().collect(Collectors.toSet());
    }

    @Override
    public Optional<StatisticAnnotationMeasure> findById(String id) {
        return this.statisticAnnotationMeasureJpa.findById(id);
    }

    @Override
    public Optional<StatisticAnnotationMeasure> findByName(String name) {
        return this.statisticAnnotationMeasureJpa.findByName(name);
    }
    
}
