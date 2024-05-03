package de.garrafao.phitag.domain.joblisting;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;

public interface JoblistingRepository {
    
    List<Joblisting> findByQuery(final Query query);

    Page<Joblisting> findByQueryPaged(final Query query, final PageRequestWraper page);

    Optional<Joblisting> findByIdNameAndIdProjectidNameAndIdProjectidOwnername(String name, String projectname, String ownername);
    
    Joblisting save(Joblisting joblisting);
}
