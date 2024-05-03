package de.garrafao.phitag.infrastructure.persistence.jpa.instance.usepairinstance;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.instance.usepairinstance.UsePairInstance;
import de.garrafao.phitag.domain.instance.usepairinstance.UsePairInstanceRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.instance.usepairinstance.query.UsePairInstanceQueryJpa;

@Repository
public class UsePairInstanceRepositoryBridge implements UsePairInstanceRepository {
    
    private final UsePairInstanceRepositoryJpa usePairInstanceRepositoryJpa;

    @Autowired
    public UsePairInstanceRepositoryBridge(UsePairInstanceRepositoryJpa usePairInstanceRepositoryJpa) {
        this.usePairInstanceRepositoryJpa = usePairInstanceRepositoryJpa;
    }

    @Override
    public List<UsePairInstance> findByQuery(Query query) {
        return this.usePairInstanceRepositoryJpa.findAll(new UsePairInstanceQueryJpa(query));
    }

    @Override
    public Page<UsePairInstance> findByQueryPaged(Query query, PageRequestWraper page) {
        return this.usePairInstanceRepositoryJpa.findAll(new UsePairInstanceQueryJpa(query), page.getPageRequest());
    }

    @Override
    public Optional<UsePairInstance> findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(
            String instanceId, String phaseName, String projectName, String ownerName) {
        return this.usePairInstanceRepositoryJpa.findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(instanceId, phaseName, projectName, ownerName);
    }


    @Override
    public UsePairInstance save(UsePairInstance usePairInstance) {
        return this.usePairInstanceRepositoryJpa.save(usePairInstance);
    }

    @Override
    public void delete(Iterable<UsePairInstance> instances) {
        this.usePairInstanceRepositoryJpa.deleteInBatch(instances);
    }

}
