package de.garrafao.phitag.infrastructure.persistence.jpa.statistic.projectstatistic;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.garrafao.phitag.domain.statistic.projectstatistic.ProjectStatistic;
import de.garrafao.phitag.domain.statistic.projectstatistic.ProjectStatisticId;

public interface ProjectStatisticRepositoryJpa extends JpaRepository<ProjectStatistic, ProjectStatisticId>  {

    Optional<ProjectStatistic> findByUsernameAndProjectname(final String username, final String projectname);
    
}
