package de.garrafao.phitag.infrastructure.persistence.jpa.annotationtype;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.garrafao.phitag.domain.annotationtype.AnnotationType;

public interface AnnotationTypeJpa extends JpaRepository<AnnotationType, Integer> {
    
    public Optional<AnnotationType> findByName(String name);
    
}
