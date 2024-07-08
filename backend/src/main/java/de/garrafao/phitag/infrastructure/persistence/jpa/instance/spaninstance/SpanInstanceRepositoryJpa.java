package de.garrafao.phitag.infrastructure.persistence.jpa.instance.spaninstance;

import de.garrafao.phitag.domain.instance.spaninstance.SpanInstance;
import de.garrafao.phitag.domain.instance.spaninstance.SpanInstanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SpanInstanceRepositoryJpa extends JpaRepository<SpanInstance, SpanInstanceId>, JpaSpecificationExecutor<SpanInstance> {

    Optional<SpanInstance> findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(
            String instanceId, String phaseName, String projectName, String ownerName);
    
}
