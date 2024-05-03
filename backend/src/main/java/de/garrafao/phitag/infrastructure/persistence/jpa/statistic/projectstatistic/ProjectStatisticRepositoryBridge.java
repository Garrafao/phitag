package de.garrafao.phitag.infrastructure.persistence.jpa.statistic.projectstatistic;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.statistic.projectstatistic.ProjectStatistic;
import de.garrafao.phitag.domain.statistic.projectstatistic.ProjectStatisticRepository;

@Repository
public class ProjectStatisticRepositoryBridge implements ProjectStatisticRepository {

    private final ProjectStatisticRepositoryJpa projectStatisticRepositoryJpa;

    @Autowired
    public ProjectStatisticRepositoryBridge(ProjectStatisticRepositoryJpa projectStatisticRepositoryJpa) {
        this.projectStatisticRepositoryJpa = projectStatisticRepositoryJpa;
    }

    @Override
    public Optional<ProjectStatistic> findByUsernameAndProjectname(String username, String projectname) {
        return projectStatisticRepositoryJpa.findByUsernameAndProjectname(username, projectname);
    }

    @Override
    public ProjectStatistic save(ProjectStatistic projectStatistic) {
        return projectStatisticRepositoryJpa.save(projectStatistic);
    }
   
}
