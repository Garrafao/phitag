package de.garrafao.phitag.domain.instance.userankpairinstances;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UseRankPairRepository {

    List<UseRankPairInstance> findByQuery(final Query query);

    Page<UseRankPairInstance> findByQueryPaged(final Query query, final PageRequestWraper page);

    Optional<UseRankPairInstance> findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(final String instanceId, final String phaseName, final String projectName, final String ownerName);

    UseRankPairInstance save(UseRankPairInstance instanceData);

    void delete(Iterable<UseRankPairInstance> instanceData);

}
