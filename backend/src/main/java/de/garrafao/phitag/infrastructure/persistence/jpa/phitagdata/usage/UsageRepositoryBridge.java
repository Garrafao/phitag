package de.garrafao.phitag.infrastructure.persistence.jpa.phitagdata.usage;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.phitagdata.usage.Usage;
import de.garrafao.phitag.domain.phitagdata.usage.UsageRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.phitagdata.usage.query.UsageQueryJpa;

@Repository
public class UsageRepositoryBridge implements UsageRepository {

    private final UsageRepositoryJpa usageRepositoryJpa;

    @Autowired
    public UsageRepositoryBridge(UsageRepositoryJpa usageRepositoryJpa) {
        this.usageRepositoryJpa = usageRepositoryJpa;
    }

    @Override
    public List<Usage> findByQuery(Query query) {
        return usageRepositoryJpa.findAll(new UsageQueryJpa(query));
    }

    @Override
    public Page<Usage> findByQueryPaged(Query query, PageRequestWraper page) {
        return usageRepositoryJpa.findAll(new UsageQueryJpa(query), page.getPageRequest());
    }

    @Override
    public Optional<Usage> findByIdDataidAndIdProjectidNameAndIdProjectidOwnername(String dataid, String projectname,
            String ownername) {
        return usageRepositoryJpa.findByIdDataidAndIdProjectidNameAndIdProjectidOwnername(dataid, projectname,
                ownername);
    }


    @Override
    public List<String> findAllDataIdsByProjectnameAndOwnername(String projectname, String ownername) {
        return usageRepositoryJpa.findAllDataIdsByProjectnameAndOwnername(projectname, ownername);
    }

    @Override
    public Usage save(Usage usage) {
        return this.usageRepositoryJpa.save(usage);
    }

    // For statistics

    @Override
    public Integer countDistinctLemmaByIdProjectidNameAndIdProjectidOwnername(String projectname, String ownername) {
        return usageRepositoryJpa.countDistinctLemmaByIdProjectidNameAndIdProjectidOwnername(projectname, ownername);
    }

    @Override
    public Integer countByIdProjectidNameAndIdProjectidOwnername(String projectname, String ownername) {
        return usageRepositoryJpa.countByIdProjectidNameAndIdProjectidOwnername(projectname, ownername);
    }

    @Override
    public List<String> findDistinctLemmaByIdProjectidNameAndIdProjectidOwnername(String projectname,
            String ownername) {
        return usageRepositoryJpa.findDistinctLemmaByIdProjectidNameAndIdProjectidOwnername(projectname, ownername);
    }

    @Override
    public Integer countDistinctLemmaByLemmaAndIdProjectidNameAndIdProjectidOwnername(String lemma, String projectname,
            String ownername) {
        return usageRepositoryJpa.countDistinctLemmaByLemmaAndIdProjectidNameAndIdProjectidOwnername(lemma, projectname,
                ownername);
    }

}
