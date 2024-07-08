package de.garrafao.phitag.infrastructure.persistence.jpa.instance.spaninstance;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.instance.spaninstance.SpanInstance;
import de.garrafao.phitag.domain.instance.spaninstance.SpanInstanceRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.instance.spaninstance.query.SpanInstanceQueryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SpanInstanceRepositoryBridge implements SpanInstanceRepository {

    private final SpanInstanceRepositoryJpa spanInstanceRepositoryJpa;

    @Autowired
    public SpanInstanceRepositoryBridge(SpanInstanceRepositoryJpa spanInstanceRepositoryJpa) {
        this.spanInstanceRepositoryJpa = spanInstanceRepositoryJpa;
    }

    @Override
    public List<SpanInstance> findByQuery(Query query) {
        return this.spanInstanceRepositoryJpa.findAll(new SpanInstanceQueryJpa(query));
    }

    @Override
    public Page<SpanInstance> findByQueryPaged(Query query, PageRequestWraper page) {
        return this.spanInstanceRepositoryJpa.findAll(new SpanInstanceQueryJpa(query), page.getPageRequest());
    }

    @Override
    public Optional<SpanInstance> findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(
            String instanceId, String phaseName, String projectName, String ownerName) {
        return this.spanInstanceRepositoryJpa
                .findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(instanceId,
                        phaseName, projectName, ownerName);
    }

    @Override
    public SpanInstance save(SpanInstance instanceData) {
        return this.spanInstanceRepositoryJpa.save(instanceData);
    }

    @Override
    public void delete(Iterable<SpanInstance> instanceData) {
        this.spanInstanceRepositoryJpa.deleteInBatch(instanceData);
    }

    @Override
    public void delete(SpanInstance instanceData) {
        this.spanInstanceRepositoryJpa.delete(instanceData);
    }
}
