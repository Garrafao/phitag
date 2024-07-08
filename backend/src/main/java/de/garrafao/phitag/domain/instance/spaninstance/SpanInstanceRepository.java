package de.garrafao.phitag.domain.instance.spaninstance;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface SpanInstanceRepository {

    List<SpanInstance> findByQuery(final Query query);
    
    Page<SpanInstance> findByQueryPaged(final Query query, final PageRequestWraper page);

    Optional<SpanInstance> findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(final String instanceId, final String phaseName, final String projectName, final String ownerName);

    SpanInstance save(SpanInstance instanceData);

    void delete(Iterable<SpanInstance> instanceData);

    void delete(SpanInstance instanceData);
}
