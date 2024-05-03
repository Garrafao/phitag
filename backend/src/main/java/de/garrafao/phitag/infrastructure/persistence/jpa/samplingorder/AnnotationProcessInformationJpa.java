package de.garrafao.phitag.infrastructure.persistence.jpa.samplingorder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import de.garrafao.phitag.domain.annotationprocessinformation.AnnotationProcessInformation;
import de.garrafao.phitag.domain.annotationprocessinformation.AnnotationProcessInformationId;

public interface AnnotationProcessInformationJpa extends JpaRepository<AnnotationProcessInformation, AnnotationProcessInformationId>, JpaSpecificationExecutor<AnnotationProcessInformation> {
    
    
}
