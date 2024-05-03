package de.garrafao.phitag.infrastructure.rest.statistic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.statistics.statisticannotationmeasure.StatisticAnnotationMeasureApplicationService;
import de.garrafao.phitag.application.statistics.statisticannotationmeasure.data.StatisticAnnotationMeasureDto;

@RestController
@RequestMapping(value = "/api/v1/statistic/annotationmeasure")
public class StatisticAnnotationMeasureResource {

    private final StatisticAnnotationMeasureApplicationService service;

    @Autowired
    public StatisticAnnotationMeasureResource(StatisticAnnotationMeasureApplicationService service) {
        this.service = service;
    }

    @RequestMapping()
    public List<StatisticAnnotationMeasureDto> all() {
        return this.service.getStatisticAnnotationMeasures();
    }
    
}
