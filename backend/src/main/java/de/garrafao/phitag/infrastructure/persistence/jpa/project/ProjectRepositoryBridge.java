package de.garrafao.phitag.infrastructure.persistence.jpa.project;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.project.Project;
import de.garrafao.phitag.domain.project.ProjectRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.project.query.ProjectQueryJpa;

@Repository
public class ProjectRepositoryBridge implements ProjectRepository {

    private final ProjectRepositoryJpa projectRepositoryJpa;

    @Autowired
    public ProjectRepositoryBridge(ProjectRepositoryJpa projectRepositoryJpa) {
        this.projectRepositoryJpa = projectRepositoryJpa;
    }

    @Override
    public Project save(Project project) {
        return projectRepositoryJpa.save(project);
    }

    @Override
    public List<Project> findByQuery(Query query) {
        ProjectQueryJpa projectQueryJpa = new ProjectQueryJpa(query);

        return projectRepositoryJpa.findAll(projectQueryJpa);
    }

    @Override
    public Page<Project> findByQueryPaged(Query query, PageRequestWraper page) {
        ProjectQueryJpa projectQueryJpa = new ProjectQueryJpa(query);

        return projectRepositoryJpa.findAll(projectQueryJpa, page.getPageRequest());

    }

    @Override
    public Optional<Project> findByIdNameAndIdOwnername(String name, String ownername) {
        return projectRepositoryJpa.findByIdNameAndIdOwnername(name, ownername);
    }

    @Override
    public void delete(final Project project) {
        projectRepositoryJpa.delete(project);
    }

}
