package de.garrafao.phitag.application.statistics.statistictask.data.dto;

import de.garrafao.phitag.domain.statistic.statistictask.StatisticTask;
import lombok.Getter;

@Getter
public class StatisticTaskDto {

    private Integer id;

    private String targetOwnername;
    private String targetProjectname;
    private String targetPhasename;
    private String targetAnnotatorname;

    private String status;

    private StatisticTaskDto(
            final Integer id,
            final String targetOwnername,
            final String targetProjectname,
            final String targetPhasename,
            final String targetAnnotatorname,
            final String status) {
        this.id = id;
        this.targetOwnername = targetOwnername;
        this.targetProjectname = targetProjectname;
        this.targetPhasename = targetPhasename;
        this.targetAnnotatorname = targetAnnotatorname;
        this.status = status;
    }

    public static StatisticTaskDto from(StatisticTask task) {
        if (task == null) {
            return null;
        }
        return new StatisticTaskDto(
                task.getId(),
                task.getTargetOwnername(),
                task.getTargetProjectname(),
                task.getTargetPhasename(),
                task.getTargetAnnotatorname(),
                task.getStatus().getName()
        );
    }

}
