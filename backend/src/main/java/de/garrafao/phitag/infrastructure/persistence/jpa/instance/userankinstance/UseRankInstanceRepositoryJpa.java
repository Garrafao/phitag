package de.garrafao.phitag.infrastructure.persistence.jpa.instance.userankinstance;

import de.garrafao.phitag.domain.instance.userankinstance.UseRankInstance;
import de.garrafao.phitag.domain.instance.userankinstance.UseRankInstanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UseRankInstanceRepositoryJpa extends JpaRepository<UseRankInstance, UseRankInstanceId>,JpaSpecificationExecutor<UseRankInstance>

    {

        Optional<UseRankInstance> findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(
            String instanceId, String phaseName, String projectName, String ownerName);

    }
