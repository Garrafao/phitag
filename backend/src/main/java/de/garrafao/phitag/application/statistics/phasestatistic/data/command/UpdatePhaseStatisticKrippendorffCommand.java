package de.garrafao.phitag.application.statistics.phasestatistic.data.command;

import java.util.Map;

import lombok.Getter;

@Getter
public final class UpdatePhaseStatisticKrippendorffCommand {

    private final String ownername;
    private final String projectname;
    private final String phasename;

    private final Integer statisticTaskId;

    private final Double krippendorffalpha;
    private final Map<String, Double> interannotatorKrippendorffalpha;

    public UpdatePhaseStatisticKrippendorffCommand(
            final String ownername,
            final String projectname,
            final String phasename,

            final Integer statisticTaskId,

            final Double krippendorffalpha,
            final Map<String, Double> interannotatorKrippendorffalpha) {
        this.ownername = ownername;
        this.projectname = projectname;
        this.phasename = phasename;

        this.statisticTaskId = statisticTaskId;

        this.krippendorffalpha = krippendorffalpha;
        this.interannotatorKrippendorffalpha = interannotatorKrippendorffalpha;
    }

}
