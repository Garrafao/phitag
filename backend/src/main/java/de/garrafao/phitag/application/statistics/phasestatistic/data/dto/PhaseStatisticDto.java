package de.garrafao.phitag.application.statistics.phasestatistic.data.dto;

import java.util.Map;

import de.garrafao.phitag.domain.statistic.phasestatistic.PhaseStatistic;
import lombok.Getter;

@Getter
public class PhaseStatisticDto {

    private final String ownername;
    private final String projectname;
    private final String phasename;

    private final Integer annotations;
    private final Map<String, Integer> annotatorAnnotationCountMap;

    private final Double krippendorffalpha;
    private final Map<String, Double> interannotatorKrippendorffalpha;

    public PhaseStatisticDto(
            final String ownername,
            final String projectname,
            final String phasename,

            final Integer annotations,
            final Map<String, Integer> annotatorAnnotationCountMap,

            final Double krippendorffalpha,
            final Map<String, Double> interannotatorKrippendorffalpha) {
        this.ownername = ownername;
        this.projectname = projectname;
        this.phasename = phasename;

        this.annotations = annotations;
        this.annotatorAnnotationCountMap = annotatorAnnotationCountMap;

        this.krippendorffalpha = krippendorffalpha;
        this.interannotatorKrippendorffalpha = interannotatorKrippendorffalpha;
    }

    public static PhaseStatisticDto from(PhaseStatistic phaseStatistic) {
        return new PhaseStatisticDto(
                phaseStatistic.getOwnername(),
                phaseStatistic.getProjectname(),
                phaseStatistic.getPhasename(),
                phaseStatistic.getAnnotations(),
                phaseStatistic.getAnnotatorAnnotationCountMap(),
                phaseStatistic.getKrippendorffalpha(),
                phaseStatistic.getInterannotatorKrippendorffalpha());
    }

}
