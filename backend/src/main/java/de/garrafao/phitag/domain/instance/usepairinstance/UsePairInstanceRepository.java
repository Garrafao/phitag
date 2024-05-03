package de.garrafao.phitag.domain.instance.usepairinstance;

import java.util.List;
import java.util.Optional;

import de.garrafao.phitag.domain.phase.Phase;
import org.springframework.data.domain.Page;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;

public interface UsePairInstanceRepository {

    List<UsePairInstance> findByQuery(final Query query);

    Page<UsePairInstance> findByQueryPaged(final Query query, final PageRequestWraper page);
    
    Optional<UsePairInstance> findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(final String instanceId, final String phaseName, final String projectName, final String ownerName);
    
    UsePairInstance save(UsePairInstance instanceData);

    void delete(Iterable<UsePairInstance> instanceData);

}
