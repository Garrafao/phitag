package de.garrafao.phitag.domain.project;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;

public interface ProjectRepository {

    List<Project> findByQuery(Query query);

    Page<Project> findByQueryPaged(Query query, PageRequestWraper page);

    Optional<Project> findByIdNameAndIdOwnername(String name, String ownername);

    Project save(Project project);

    void delete(Project project);

}
