package de.garrafao.phitag.infrastructure.persistence.jpa.annotationtype;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.annotationtype.AnnotationType;
import de.garrafao.phitag.domain.annotationtype.AnnotationTypeRepository;

@Repository
public class AnnotationTypeBridge implements AnnotationTypeRepository {

    private AnnotationTypeJpa annotationTypeJpa;

    @Autowired
    public AnnotationTypeBridge(AnnotationTypeJpa annotationTypeJpa) {
        this.annotationTypeJpa = annotationTypeJpa;
    }


    @Override
    public Optional<AnnotationType> findByName(String name) {
        return this.annotationTypeJpa.findByName(name);
    }


    @Override
    public Set<AnnotationType> findAll() {
        return this.annotationTypeJpa.findAll().stream().collect(Collectors.toSet());
    }
    
}
