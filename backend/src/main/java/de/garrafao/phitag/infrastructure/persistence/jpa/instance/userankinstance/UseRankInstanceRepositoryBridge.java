package de.garrafao.phitag.infrastructure.persistence.jpa.instance.userankinstance;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.instance.userankinstance.UseRankInstance;
import de.garrafao.phitag.domain.instance.userankinstance.UseRankRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.instance.userankinstance.query.UseRankInstanceQueryJpa;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UseRankInstanceRepositoryBridge implements UseRankRepository {

    private final UseRankInstanceRepositoryJpa useRankInstanceRepositoryJpa;

    public UseRankInstanceRepositoryBridge(UseRankInstanceRepositoryJpa useRankInstanceRepositoryJpa) {
        this.useRankInstanceRepositoryJpa = useRankInstanceRepositoryJpa;
    }


    @Override
    public List<UseRankInstance> findByQuery(Query query) {
        return this.useRankInstanceRepositoryJpa.findAll(new UseRankInstanceQueryJpa(query));
    }

    @Override
    public Page<UseRankInstance> findByQueryPaged(Query query, PageRequestWraper page) {

        return this.useRankInstanceRepositoryJpa.findAll(new UseRankInstanceQueryJpa(query), page.getPageRequest());
    }

    @Override
    public Optional<UseRankInstance> findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(String instanceId, String phaseName, String projectName, String ownerName) {
        return this.useRankInstanceRepositoryJpa.findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(instanceId, phaseName, projectName, ownerName);
    }

    @Override
    public UseRankInstance save(UseRankInstance instanceData) {
        return this.useRankInstanceRepositoryJpa.save(instanceData);
    }

    @Override
    public void delete(Iterable<UseRankInstance> instanceData) {
        this.useRankInstanceRepositoryJpa.deleteInBatch(instanceData);
    }
}
