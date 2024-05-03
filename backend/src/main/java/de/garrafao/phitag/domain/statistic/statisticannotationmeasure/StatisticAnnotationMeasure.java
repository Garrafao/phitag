package de.garrafao.phitag.domain.statistic.statisticannotationmeasure;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@Table(name = "phitagstatisticannotationmeasures")
@Getter
@EqualsAndHashCode
public class StatisticAnnotationMeasure {

    @Id
    private String id;

    private String name;

    private StatisticAnnotationMeasure() {

    }

    public StatisticAnnotationMeasure(final String id, final String name) {
        this.id = id;
        this.name = name;
    }


}
