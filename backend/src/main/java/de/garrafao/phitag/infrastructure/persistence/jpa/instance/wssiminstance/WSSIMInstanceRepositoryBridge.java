package de.garrafao.phitag.infrastructure.persistence.jpa.instance.wssiminstance;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.instance.wssiminstance.WSSIMInstance;
import de.garrafao.phitag.domain.instance.wssiminstance.WSSIMInstanceRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.instance.wssiminstance.query.WSSIMInstanceQueryJpa;

@Repository
public class WSSIMInstanceRepositoryBridge implements WSSIMInstanceRepository {
    
    private final WSSIMInstanceRepositoryJpa wssimInstanceRepositoryJpa;

    @Autowired
    public WSSIMInstanceRepositoryBridge(WSSIMInstanceRepositoryJpa wssimInstanceRepositoryJpa) {
        this.wssimInstanceRepositoryJpa = wssimInstanceRepositoryJpa;
    }

    @Override
    public List<WSSIMInstance> findByQuery(Query query) {
        return this.wssimInstanceRepositoryJpa.findAll(new WSSIMInstanceQueryJpa(query));
    }

    @Override
    public Page<WSSIMInstance> findByQueryPaged(Query query, PageRequestWraper page) {
        return this.wssimInstanceRepositoryJpa.findAll(new WSSIMInstanceQueryJpa(query), page.getPageRequest());
    }

    @Override
    public Optional<WSSIMInstance> findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(
            String instanceId, String phaseName, String projectName, String ownerName) {
        return this.wssimInstanceRepositoryJpa.findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(instanceId, phaseName, projectName, ownerName);
    }

    @Override
    public WSSIMInstance save(WSSIMInstance instanceData) {
        return this.wssimInstanceRepositoryJpa.save(instanceData);
    }
}
