package de.garrafao.phitag.domain.annotator;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;

public interface AnnotatorRepository {
    
    List<Annotator> findByQuery(Query query);

    Page<Annotator> findByQueryPaged(final Query query, final PageRequestWraper page);

    Optional<Annotator> findByIdUsernameAndIdProjectidNameAndIdProjectidOwnername(String name, String projectname, String ownername);

    Annotator save(Annotator annotator);

    void delete(Annotator annotator);
}
