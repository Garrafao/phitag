package de.garrafao.phitag.infrastructure.persistence.jpa.joblisting;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import de.garrafao.phitag.domain.joblisting.Joblisting;
import de.garrafao.phitag.domain.joblisting.JoblistingId;

public interface JoblistingRepositoryJpa extends JpaRepository<Joblisting, JoblistingId>, JpaSpecificationExecutor<Joblisting> {

    Optional<Joblisting> findByIdNameAndIdProjectidNameAndIdProjectidOwnername(String name, String projectname,
            String ownername);

    
}
