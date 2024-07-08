package de.garrafao.phitag.application.statistics.annotatorhistorytable.data.command;

import de.garrafao.phitag.domain.statistic.statisticannotationmeasure.StatisticAnnotationMeasure;
import lombok.Getter;

@Getter
public class AddAnnotatorStaticHistoryCommand {

    private final String annotatorname;
    private final String goldannotator;
    private final String ownername;
    private final String projectname;
    private final String phasename;
    private  final StatisticAnnotationMeasure statisticAnnotationMeasure;

    public AddAnnotatorStaticHistoryCommand(String annotatorname, String goldannotator, String ownername, String projectname, String phasename,
                                            StatisticAnnotationMeasure statisticAnnotationMeasure) {
        this.annotatorname = annotatorname;
        this.goldannotator = goldannotator;
        this.ownername = ownername;
        this.projectname = projectname;
        this.phasename = phasename;
        this.statisticAnnotationMeasure = statisticAnnotationMeasure;
    }
}
