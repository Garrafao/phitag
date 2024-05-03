package de.garrafao.phitag.domain.instance.userankrelative;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UseRankRelativeInstanceRepository {

    List<UseRankRelativeInstance> findByQuery(final Query query);

    Page<UseRankRelativeInstance> findByQueryPaged(final Query query, final PageRequestWraper page);

    Optional<UseRankRelativeInstance> findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(final String instanceId, final String phaseName, final String projectName, final String ownerName);

    UseRankRelativeInstance save(UseRankRelativeInstance instanceData);

}
