package de.garrafao.phitag.application.statistics.statisticannotationmeasure.data;

import de.garrafao.phitag.domain.statistic.statisticannotationmeasure.StatisticAnnotationMeasure;
import lombok.Getter;

@Getter
public class StatisticAnnotationMeasureDto {

    private final String id;
    private final String name;

    private StatisticAnnotationMeasureDto(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    public static StatisticAnnotationMeasureDto from(final StatisticAnnotationMeasure statisticAnnotationMeasure) {
        return new StatisticAnnotationMeasureDto(
                statisticAnnotationMeasure.getId(),
                statisticAnnotationMeasure.getName());
    }

}
