package de.garrafao.phitag.domain.phase;

import java.util.List;
import java.util.Optional;

import de.garrafao.phitag.domain.instance.IInstance;
import org.springframework.data.domain.Page;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;

public interface PhaseRepository {

    List<Phase> findByQuery(final Query query);

    Page<Phase> findByQueryPaged(final Query query, final PageRequestWraper page);

    Optional<Phase> findByIdNameAndIdProjectidNameAndIdProjectidOwnername(String name, String projectname, String ownername);

    Phase save(Phase phase);

    void  delete(Phase phase);

}
