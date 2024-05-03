package de.garrafao.phitag.infrastructure.persistence.jpa.instance.wssimtag;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.instance.wssimtag.WSSIMTag;
import de.garrafao.phitag.domain.instance.wssimtag.WSSIMTagRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.instance.wssimtag.query.WSSIMTagQueryJpa;

@Repository
public class WSSIMTagRepositoryBridge implements WSSIMTagRepository {

    private final WSSIMTagRepositoryJpa wssimTagRepositoryJpa;

    @Autowired
    public WSSIMTagRepositoryBridge(WSSIMTagRepositoryJpa wssimTagRepositoryJpa) {
        this.wssimTagRepositoryJpa = wssimTagRepositoryJpa;
    }

    @Override
    public List<WSSIMTag> findByQuery(Query query) {
        return wssimTagRepositoryJpa.findAll(new WSSIMTagQueryJpa(query));
    }

    @Override
    public Page<WSSIMTag> findByQueryPaged(Query query, PageRequestWraper page) {
        return wssimTagRepositoryJpa.findAll(new WSSIMTagQueryJpa(query), page.getPageRequest());

    }

    @Override
    public Optional<WSSIMTag> findByIdTagidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(
            String instanceId, String phaseName, String projectName, String ownerName) {
        return this.wssimTagRepositoryJpa.findByIdTagidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(instanceId, phaseName, projectName, ownerName);
    }

    @Override
    public WSSIMTag save(WSSIMTag instanceData) {
        return this.wssimTagRepositoryJpa.save(instanceData);
    }

}
