package de.garrafao.phitag.domain.statistic.projectstatistic;

import java.util.Optional;

public interface ProjectStatisticRepository {

    public Optional<ProjectStatistic> findByUsernameAndProjectname(final String username, final String projectname);

    public ProjectStatistic save(final ProjectStatistic projectStatistic);
    
}
