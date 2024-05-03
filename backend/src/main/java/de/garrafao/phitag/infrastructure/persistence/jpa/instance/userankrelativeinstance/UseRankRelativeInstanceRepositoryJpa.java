package de.garrafao.phitag.infrastructure.persistence.jpa.instance.userankrelativeinstance;

import de.garrafao.phitag.domain.instance.userankrelative.UseRankRelativeInstance;
import de.garrafao.phitag.domain.instance.userankrelative.UseRankRelativeInstanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UseRankRelativeInstanceRepositoryJpa extends JpaRepository<UseRankRelativeInstance, UseRankRelativeInstanceId>,JpaSpecificationExecutor<UseRankRelativeInstance>

    {

        Optional<UseRankRelativeInstance> findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(
            String instanceId, String phaseName, String projectName, String ownerName);

    }
