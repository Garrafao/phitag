package de.garrafao.phitag.infrastructure.persistence.jpa.phase;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.phase.PhaseId;

public interface PhaseRepositoryJpa extends JpaRepository<Phase, PhaseId>, JpaSpecificationExecutor<Phase> {

    Optional<Phase> findByIdNameAndIdProjectidNameAndIdProjectidOwnername(String name,
            String projectname, String ownername);
}
