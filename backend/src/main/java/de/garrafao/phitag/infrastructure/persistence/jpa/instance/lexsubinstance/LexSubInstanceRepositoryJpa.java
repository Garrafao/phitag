package de.garrafao.phitag.infrastructure.persistence.jpa.instance.lexsubinstance;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import de.garrafao.phitag.domain.instance.lexsub.LexSubInstance;
import de.garrafao.phitag.domain.instance.lexsub.LexSubInstanceId;

public interface LexSubInstanceRepositoryJpa extends JpaRepository<LexSubInstance, LexSubInstanceId>, JpaSpecificationExecutor<LexSubInstance> {

    Optional<LexSubInstance> findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(
            String instanceId, String phaseName, String projectName, String ownerName);
    
}
