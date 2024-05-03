package de.garrafao.phitag.application.statistics.annotatostatistic.data.dto;

import java.util.Map;

import de.garrafao.phitag.domain.statistic.annotatorstatistic.AnnotatorStatistic;
import lombok.Getter;

@Getter
public class AnnotatorStatisticDto {

    private final String annotatorname;
    private final String ownername;
    private final String projectname;

    private final Double krippendorffalpha;
    private final Map<String, Double> interannotatorKrippendorffalpha;

    private final Integer annotations;
    private final Map<String, Integer> phaseAnnotationCountMap;

    public AnnotatorStatisticDto(
            final String annotatorname,
            final String ownername,
            final String projectname,

            final Double krippendorffalpha,
            final Map<String, Double> interannotatorKrippendorffalpha,

            final Integer annotations,
            final Map<String, Integer> phaseAnnotationCountMap) {
        this.annotatorname = annotatorname;
        this.ownername = ownername;
        this.projectname = projectname;

        this.krippendorffalpha = krippendorffalpha;
        this.interannotatorKrippendorffalpha = interannotatorKrippendorffalpha;

        this.annotations = annotations;
        this.phaseAnnotationCountMap = phaseAnnotationCountMap;
    }

    public static AnnotatorStatisticDto from(AnnotatorStatistic annotatorStatistic) {
        return new AnnotatorStatisticDto(
                annotatorStatistic.getAnnotatorname(),
                annotatorStatistic.getOwnername(),
                annotatorStatistic.getProjectname(),
                annotatorStatistic.getKrippendorffalpha(),
                annotatorStatistic.getInterannotatorKrippendorffalpha(),
                annotatorStatistic.getAnnotations(),
                annotatorStatistic.getPhaseAnnotationCountMap());
    }

}
