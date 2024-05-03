package de.garrafao.phitag.infrastructure.persistence.jpa.instance.userankpairinstance;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.instance.userankpairinstances.UseRankPairInstance;
import de.garrafao.phitag.domain.instance.userankpairinstances.UseRankPairRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.instance.userankpairinstance.query.UseRankPairInstanceQueryJpa;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UseRankPairInstanceRepositoryBridge implements UseRankPairRepository {

    private final UseRankPairInstanceRepositoryJpa useRankPairInstanceRepositoryJpa;

    public UseRankPairInstanceRepositoryBridge(UseRankPairInstanceRepositoryJpa useRankPairInstanceRepositoryJpa) {
        this.useRankPairInstanceRepositoryJpa = useRankPairInstanceRepositoryJpa;
    }


    @Override
    public List<UseRankPairInstance> findByQuery(Query query) {
        return this.useRankPairInstanceRepositoryJpa.findAll(new UseRankPairInstanceQueryJpa(query));
    }

    @Override
    public Page<UseRankPairInstance> findByQueryPaged(Query query, PageRequestWraper page) {

        return this.useRankPairInstanceRepositoryJpa.findAll(new UseRankPairInstanceQueryJpa(query), page.getPageRequest());
    }

    @Override
    public Optional<UseRankPairInstance> findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(String instanceId, String phaseName, String projectName, String ownerName) {
        return this.useRankPairInstanceRepositoryJpa.findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(instanceId, phaseName, projectName, ownerName);
    }

    @Override
    public UseRankPairInstance save(UseRankPairInstance instanceData) {
        return this.useRankPairInstanceRepositoryJpa.save(instanceData);
    }

    @Override
    public void delete(Iterable<UseRankPairInstance> instanceData) {
        this.useRankPairInstanceRepositoryJpa.deleteInBatch(instanceData);
    }
}
