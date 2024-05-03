package de.garrafao.phitag.infrastructure.persistence.jpa.instance.wssiminstance;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import de.garrafao.phitag.domain.instance.wssiminstance.WSSIMInstance;
import de.garrafao.phitag.domain.instance.wssiminstance.WSSIMInstanceId;

public interface WSSIMInstanceRepositoryJpa extends JpaRepository<WSSIMInstance, WSSIMInstanceId>, JpaSpecificationExecutor<WSSIMInstance> {
    
    Optional<WSSIMInstance> findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(
            String instanceId, String phaseName, String projectName, String ownerName);
}
