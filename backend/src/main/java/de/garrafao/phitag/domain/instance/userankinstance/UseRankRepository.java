package de.garrafao.phitag.domain.instance.userankinstance;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UseRankRepository {

    List<UseRankInstance> findByQuery(final Query query);

    Page<UseRankInstance> findByQueryPaged(final Query query, final PageRequestWraper page);

    Optional<UseRankInstance> findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(final String instanceId, final String phaseName, final String projectName, final String ownerName);

    UseRankInstance save(UseRankInstance instanceData);

    void delete(Iterable<UseRankInstance> instanceData);

}
