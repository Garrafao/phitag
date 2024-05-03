package de.garrafao.phitag.infrastructure.persistence.jpa.instance.userankrelativeinstance;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.instance.userankrelative.UseRankRelativeInstance;
import de.garrafao.phitag.domain.instance.userankrelative.UseRankRelativeInstanceRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.instance.userankrelativeinstance.query.UseRankRelativeInstanceQueryJpa;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UseRankRelativeInstanceRepositoryBridge implements UseRankRelativeInstanceRepository {

    private final UseRankRelativeInstanceRepositoryJpa useRankRelativeInstanceRepositoryJpa;

    public UseRankRelativeInstanceRepositoryBridge(UseRankRelativeInstanceRepositoryJpa useRankRelativeInstanceRepositoryJpa) {
        this.useRankRelativeInstanceRepositoryJpa = useRankRelativeInstanceRepositoryJpa;
    }


    @Override
    public List<UseRankRelativeInstance> findByQuery(Query query) {
        return this.useRankRelativeInstanceRepositoryJpa.findAll(new UseRankRelativeInstanceQueryJpa(query));
    }

    @Override
    public Page<UseRankRelativeInstance> findByQueryPaged(Query query, PageRequestWraper page) {

        return this.useRankRelativeInstanceRepositoryJpa.findAll(new UseRankRelativeInstanceQueryJpa(query), page.getPageRequest());
    }

    @Override
    public Optional<UseRankRelativeInstance> findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(String instanceId, String phaseName, String projectName, String ownerName) {
        return this.useRankRelativeInstanceRepositoryJpa.findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(instanceId, phaseName, projectName, ownerName);
    }

    @Override
    public UseRankRelativeInstance save(UseRankRelativeInstance instanceData) {
        return this.useRankRelativeInstanceRepositoryJpa.save(instanceData);
    }

}
