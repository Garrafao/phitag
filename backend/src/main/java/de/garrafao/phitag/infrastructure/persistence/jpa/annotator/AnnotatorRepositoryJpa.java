package de.garrafao.phitag.infrastructure.persistence.jpa.annotator;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.annotator.AnnotatorId;

public interface AnnotatorRepositoryJpa extends JpaRepository<Annotator, AnnotatorId>, JpaSpecificationExecutor<Annotator> {
    
    Optional<Annotator> findByIdUsernameAndIdProjectidNameAndIdProjectidOwnername(String name, String projectname, String ownername);
}
