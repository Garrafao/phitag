package de.garrafao.phitag.infrastructure.persistence.jpa.joblisting;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.joblisting.Joblisting;
import de.garrafao.phitag.domain.joblisting.JoblistingRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.joblisting.query.JoblistingQueryJpa;

@Repository
public class JoblistingRepositoryBridge implements JoblistingRepository {

    private final JoblistingRepositoryJpa joblistingRepositoryJpa;

    @Autowired
    public JoblistingRepositoryBridge(JoblistingRepositoryJpa joblistingRepositoryJpa) {
        this.joblistingRepositoryJpa = joblistingRepositoryJpa;
    }

    @Override
    public List<Joblisting> findByQuery(Query query) {
        return joblistingRepositoryJpa.findAll(new JoblistingQueryJpa(query));
    }

    @Override
    public Page<Joblisting> findByQueryPaged(Query query, PageRequestWraper page) {
        return joblistingRepositoryJpa.findAll(new JoblistingQueryJpa(query), page.getPageRequest());
    }

    @Override
    public Optional<Joblisting> findByIdNameAndIdProjectidNameAndIdProjectidOwnername(String name, String projectname, String ownername) {
        return joblistingRepositoryJpa.findByIdNameAndIdProjectidNameAndIdProjectidOwnername(name, projectname, ownername);
    }

    @Override
    public Joblisting save(Joblisting joblisting) {
        return joblistingRepositoryJpa.save(joblisting);
    }

    
}
