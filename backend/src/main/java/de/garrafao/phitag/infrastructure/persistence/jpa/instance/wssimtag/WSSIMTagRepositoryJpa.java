package de.garrafao.phitag.infrastructure.persistence.jpa.instance.wssimtag;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import de.garrafao.phitag.domain.instance.wssimtag.WSSIMTag;
import de.garrafao.phitag.domain.instance.wssimtag.WSSIMTagId;

public interface WSSIMTagRepositoryJpa extends JpaRepository<WSSIMTag, WSSIMTagId>, JpaSpecificationExecutor<WSSIMTag> {
    
    Optional<WSSIMTag> findByIdTagidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(
            String tagId, String phaseName, String projectName, String ownerName);
}
