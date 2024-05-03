package de.garrafao.phitag.infrastructure.persistence.jpa.project;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import de.garrafao.phitag.domain.project.Project;
import de.garrafao.phitag.domain.project.ProjectId;

public interface ProjectRepositoryJpa extends JpaRepository<Project, ProjectId>, JpaSpecificationExecutor<Project> {

    Optional<Project> findByIdNameAndIdOwnername(String name, String ownername);

}
