package de.garrafao.phitag.infrastructure.persistence.jpa.guideline;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import de.garrafao.phitag.domain.guideline.Guideline;
import de.garrafao.phitag.domain.guideline.GuidelineId;

public interface GuidelineRepositoryJpa extends JpaRepository<Guideline, GuidelineId>, JpaSpecificationExecutor<Guideline> {

    Optional<Guideline> findByIdNameAndIdProjectidNameAndIdProjectidOwnername(String name, String projectname,
            String ownername);

}
