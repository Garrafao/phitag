package de.garrafao.phitag.application.annotationtype;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.application.annotationtype.data.AnnotationTypeDto;
import de.garrafao.phitag.domain.annotationtype.AnnotationType;
import de.garrafao.phitag.domain.annotationtype.AnnotationTypeRepository;

@Service
public class AnnotationTypeApplicationService {

    private final AnnotationTypeRepository annotationTypeRepository;

    @Autowired
    public AnnotationTypeApplicationService(final AnnotationTypeRepository annotationTypeRepository) {
        this.annotationTypeRepository = annotationTypeRepository;
    }

    public Set<AnnotationType> getAnnotationTypes() {
        return this.annotationTypeRepository.findAll();
    }

    public Set<AnnotationTypeDto> getAnnotationTypeDtos() {
        return this.annotationTypeRepository.findAll().stream().filter(AnnotationType::getActive).map(AnnotationTypeDto::from).collect(Collectors.toSet());
    }

}
