package de.garrafao.phitag.domain.instance.lexsub;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;

public interface LexSubInstanceRepository {

    List<LexSubInstance> findByQuery(final Query query);
    
    Page<LexSubInstance> findByQueryPaged(final Query query, final PageRequestWraper page);

    Optional<LexSubInstance> findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(final String instanceId, final String phaseName, final String projectName, final String ownerName);

    LexSubInstance save(LexSubInstance instanceData);

    void delete(Iterable<LexSubInstance> instanceData);
}
