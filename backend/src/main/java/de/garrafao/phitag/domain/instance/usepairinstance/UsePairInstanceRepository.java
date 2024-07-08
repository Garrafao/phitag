package de.garrafao.phitag.domain.instance.usepairinstance;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UsePairInstanceRepository {

    List<UsePairInstance> findByQuery(final Query query);

    Page<UsePairInstance> findByQueryPaged(final Query query, final PageRequestWraper page);
    
    Optional<UsePairInstance> findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(final String instanceId, final String phaseName, final String projectName, final String ownerName);
    
    UsePairInstance save(UsePairInstance instanceData);

    void delete(Iterable<UsePairInstance> instanceData);

    void delete(UsePairInstance usePairInstance);

}
