package de.garrafao.phitag.domain.instance.wssimtag;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;

public interface WSSIMTagRepository {
    
    List<WSSIMTag> findByQuery(final Query query);

    Page<WSSIMTag> findByQueryPaged(final Query query, final PageRequestWraper page);

    Optional<WSSIMTag> findByIdTagidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(final String instanceId, final String phaseName, final String projectName, final String ownerName);

    WSSIMTag save(WSSIMTag instanceData);
}
