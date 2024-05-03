package de.garrafao.phitag.infrastructure.persistence.jpa.instance.lexsubinstance;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.instance.lexsub.LexSubInstance;
import de.garrafao.phitag.domain.instance.lexsub.LexSubInstanceRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.instance.lexsubinstance.query.LexSubInstanceQueryJpa;

@Repository
public class LexSubInstanceRepositoryBridge implements LexSubInstanceRepository {

    private final LexSubInstanceRepositoryJpa lexSubInstanceRepositoryJpa;

    @Autowired
    public LexSubInstanceRepositoryBridge(LexSubInstanceRepositoryJpa lexSubInstanceRepositoryJpa) {
        this.lexSubInstanceRepositoryJpa = lexSubInstanceRepositoryJpa;
    }

    @Override
    public List<LexSubInstance> findByQuery(Query query) {
        return this.lexSubInstanceRepositoryJpa.findAll(new LexSubInstanceQueryJpa(query));
    }

    @Override
    public Page<LexSubInstance> findByQueryPaged(Query query, PageRequestWraper page) {
        return this.lexSubInstanceRepositoryJpa.findAll(new LexSubInstanceQueryJpa(query), page.getPageRequest());
    }

    @Override
    public Optional<LexSubInstance> findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(
            String instanceId, String phaseName, String projectName, String ownerName) {
        return this.lexSubInstanceRepositoryJpa
                .findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(instanceId,
                        phaseName, projectName, ownerName);
    }

    @Override
    public LexSubInstance save(LexSubInstance instanceData) {
        return this.lexSubInstanceRepositoryJpa.save(instanceData);
    }

    @Override
    public void delete(Iterable<LexSubInstance> instanceData) {
        this.lexSubInstanceRepositoryJpa.deleteInBatch(instanceData);
    }

}
