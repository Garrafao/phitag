package de.garrafao.phitag.domain.guideline;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;

public interface GuidelineRepository {

    List<Guideline> findByQuery(final Query query);

    Page<Guideline> findByQueryPaged(final Query query, final PageRequestWraper page);

    Optional<Guideline> findByIdNameAndIdProjectidNameAndIdProjectidOwnername(String name, String projectname,
            String ownername);

    Guideline save(Guideline guideline);
}
