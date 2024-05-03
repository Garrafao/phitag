package de.garrafao.phitag.infrastructure.persistence.jpa.instance.usepairinstance;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import de.garrafao.phitag.domain.instance.usepairinstance.UsePairInstance;
import de.garrafao.phitag.domain.instance.usepairinstance.UsePairInstanceId;

public interface UsePairInstanceRepositoryJpa
        extends JpaRepository<UsePairInstance, UsePairInstanceId>, JpaSpecificationExecutor<UsePairInstance> {

    Optional<UsePairInstance> findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(
            String instanceId, String phaseName, String projectName, String ownerName);


}
