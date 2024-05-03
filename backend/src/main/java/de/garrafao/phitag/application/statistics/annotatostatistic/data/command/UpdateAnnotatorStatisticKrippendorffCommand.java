package de.garrafao.phitag.application.statistics.annotatostatistic.data.command;

import java.util.Map;

import lombok.Getter;

@Getter
public class UpdateAnnotatorStatisticKrippendorffCommand {

    private final String ownername;
    private final String projectname;
    private final String annotatorname;

    private final Integer statisticTaskId;

    private final Double krippendorffalpha;
    private final Map<String, Double> interannotatorKrippendorffalpha;

    public UpdateAnnotatorStatisticKrippendorffCommand(
            final String ownername,
            final String projectname,
            final String annotatorname,

            final Integer statisticTaskId,

            final Double krippendorffalpha,
            final Map<String, Double> interannotatorKrippendorffalpha) {
        this.ownername = ownername;
        this.projectname = projectname;
        this.annotatorname = annotatorname;

        this.statisticTaskId = statisticTaskId;

        this.krippendorffalpha = krippendorffalpha;
        this.interannotatorKrippendorffalpha = interannotatorKrippendorffalpha;
    }
    
}
