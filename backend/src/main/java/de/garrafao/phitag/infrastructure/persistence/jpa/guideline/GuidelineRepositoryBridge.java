package de.garrafao.phitag.infrastructure.persistence.jpa.guideline;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.guideline.Guideline;
import de.garrafao.phitag.domain.guideline.GuidelineRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.guideline.query.GuidelineQueryJpa;

@Repository
public class GuidelineRepositoryBridge implements GuidelineRepository {

    private final GuidelineRepositoryJpa guidelineRepositoryJpa;

    @Autowired
    public GuidelineRepositoryBridge(GuidelineRepositoryJpa guidelineRepositoryJpa) {
        this.guidelineRepositoryJpa = guidelineRepositoryJpa;
    }

    @Override
    public List<Guideline> findByQuery(Query query) {
        return guidelineRepositoryJpa.findAll(new GuidelineQueryJpa(query));
    }

    @Override
    public Page<Guideline> findByQueryPaged(Query query, PageRequestWraper page) {
        return guidelineRepositoryJpa.findAll(new GuidelineQueryJpa(query), page.getPageRequest());
    }

    @Override
    public Optional<Guideline> findByIdNameAndIdProjectidNameAndIdProjectidOwnername(String name, String projectname,
            String ownername) {
        return guidelineRepositoryJpa.findByIdNameAndIdProjectidNameAndIdProjectidOwnername(name, projectname, ownername);
    }
    
    @Override
    public Guideline save(Guideline guideline) {
        return guidelineRepositoryJpa.save(guideline);
    }

}
