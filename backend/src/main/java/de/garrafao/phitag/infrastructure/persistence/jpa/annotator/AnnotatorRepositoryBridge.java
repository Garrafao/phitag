package de.garrafao.phitag.infrastructure.persistence.jpa.annotator;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.annotator.AnnotatorRepository;
import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.infrastructure.persistence.jpa.annotator.query.AnnotatorQueryJpa;

@Repository
public class AnnotatorRepositoryBridge implements AnnotatorRepository {

    private final AnnotatorRepositoryJpa annotatorRepositoryJpa;

    @Autowired
    public AnnotatorRepositoryBridge(final AnnotatorRepositoryJpa annotatorRepositoryJpa) {
        this.annotatorRepositoryJpa = annotatorRepositoryJpa;
    }

    @Override
    public List<Annotator> findByQuery(Query query) {
        return annotatorRepositoryJpa.findAll(new AnnotatorQueryJpa(query));
    }

    @Override
    public Page<Annotator> findByQueryPaged(Query query, PageRequestWraper page) {
        return annotatorRepositoryJpa.findAll(new AnnotatorQueryJpa(query), page.getPageRequest());
    }

    @Override
    public Optional<Annotator> findByIdUsernameAndIdProjectidNameAndIdProjectidOwnername(String name,
            String projectname, String ownername) {
        return annotatorRepositoryJpa.findByIdUsernameAndIdProjectidNameAndIdProjectidOwnername(name, projectname, ownername);
    }

    @Override
    public Annotator save(Annotator annotator) {
        return this.annotatorRepositoryJpa.save(annotator);
    }

    @Override
    public void delete(Annotator annotator) {
        this.annotatorRepositoryJpa.delete(annotator);
    }

}
