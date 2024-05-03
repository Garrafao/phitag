package de.garrafao.phitag.application.statistics.statisticannotationmeasure;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.application.statistics.statisticannotationmeasure.data.StatisticAnnotationMeasureDto;
import de.garrafao.phitag.domain.statistic.statisticannotationmeasure.StatisticAnnotationMeasureRepository;

@Service
public class StatisticAnnotationMeasureApplicationService {
    
    private final StatisticAnnotationMeasureRepository statisticAnnotationMeasureRepository;

    @Autowired
    public StatisticAnnotationMeasureApplicationService(final StatisticAnnotationMeasureRepository statisticAnnotationMeasureRepository) {
        this.statisticAnnotationMeasureRepository = statisticAnnotationMeasureRepository;
    }

    public List<StatisticAnnotationMeasureDto> getStatisticAnnotationMeasures() {
        return this.statisticAnnotationMeasureRepository.findAll().stream().map(StatisticAnnotationMeasureDto::from).collect(Collectors.toList());
    }

}
