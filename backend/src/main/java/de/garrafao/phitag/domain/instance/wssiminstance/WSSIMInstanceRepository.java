package de.garrafao.phitag.domain.instance.wssiminstance;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface WSSIMInstanceRepository {

    List<WSSIMInstance> findByQuery(final Query query);

    Page<WSSIMInstance> findByQueryPaged(final Query query, final PageRequestWraper page);

    Optional<WSSIMInstance> findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(final String instanceId, final String phaseName, final String projectName, final String ownerName);

    WSSIMInstance save(WSSIMInstance instanceData);

    void delete(WSSIMInstance instanceData);


}
