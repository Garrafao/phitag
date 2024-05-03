package de.garrafao.phitag.domain.annotationtype;

import java.util.Optional;
import java.util.Set;

public interface AnnotationTypeRepository {

    public Set<AnnotationType> findAll();
        
    public Optional<AnnotationType> findByName(String name);
        
}
