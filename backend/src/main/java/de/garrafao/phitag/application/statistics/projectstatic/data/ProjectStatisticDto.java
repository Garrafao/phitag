package de.garrafao.phitag.application.statistics.projectstatic.data;

import java.util.Map;

import de.garrafao.phitag.domain.statistic.projectstatistic.ProjectStatistic;
import lombok.Getter;

@Getter
public class ProjectStatisticDto {
    
    private final String username;
    private final String projectname;

    private final Integer lemmacount;
    private final Integer usagecount;
    private final Map<String, Integer> usagesPerLemmaCountMap;

    public ProjectStatisticDto(
            final String username,
            final String projectname,
            final Integer lemmacount,
            final Integer usagecount,
            final Map<String, Integer> usagesPerLemmaCountMap) {
        this.username = username;
        this.projectname = projectname;
        this.lemmacount = lemmacount;
        this.usagecount = usagecount;
        this.usagesPerLemmaCountMap = usagesPerLemmaCountMap;
    }

    public static ProjectStatisticDto from(ProjectStatistic projectStatistic) {
        return new ProjectStatisticDto(
                projectStatistic.getUsername(),
                projectStatistic.getProjectname(),
                projectStatistic.getLemmacount(),
                projectStatistic.getUsagecount(),
                projectStatistic.getUsagesPerLemmaCountMap());
    }
    
}
