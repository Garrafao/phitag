package de.garrafao.phitag.infrastructure.persistence.jpa.instance.userankpairinstance;

import de.garrafao.phitag.domain.instance.userankpairinstances.UseRankPairInstance;
import de.garrafao.phitag.domain.instance.userankpairinstances.UseRankPairInstanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UseRankPairInstanceRepositoryJpa extends JpaRepository<UseRankPairInstance, UseRankPairInstanceId>,JpaSpecificationExecutor<UseRankPairInstance>

    {

        Optional<UseRankPairInstance> findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(
            String instanceId, String phaseName, String projectName, String ownerName);

    }
