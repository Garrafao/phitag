package de.garrafao.phitag.application.statistics.annotatorhistorytable.data.dto;

import de.garrafao.phitag.domain.statistic.annotatorstathistory.AnnotatorHistoryTable;
import lombok.Getter;

@Getter
public class AnnotatorStatisticHistoryDto {

    private final String uuid;
    private final String annotatorname;
    private final String goldannotator;
    private final String ownername;
    private final String projectname;
    private final String phasename;
    private final String annotationMeasure;
    private final  Double agreement;

    public AnnotatorStatisticHistoryDto(String uuid, String annotatorname, String goldannotator, String ownername, String projectname, String phasename,
                                        String annotationMeasure, Double agreement) {
        this.uuid = uuid;
        this.annotatorname = annotatorname;
        this.goldannotator = goldannotator;
        this.ownername = ownername;
        this.projectname = projectname;
        this.phasename = phasename;
        this.annotationMeasure = annotationMeasure;
        this.agreement = agreement;
    }

    public static AnnotatorStatisticHistoryDto from(AnnotatorHistoryTable annotatorHistoryTable) {
        return new AnnotatorStatisticHistoryDto(
                annotatorHistoryTable.getUuid(),
                annotatorHistoryTable.getAnnotatorname(),
                annotatorHistoryTable.getGoldannotator(),
                annotatorHistoryTable.getOwnername(),
                annotatorHistoryTable.getProjectname(),
                annotatorHistoryTable.getPhasename(),
                annotatorHistoryTable.getAnnotationMeasure(),
                annotatorHistoryTable.getAgreement());
    }

}
