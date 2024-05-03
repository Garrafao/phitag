package de.garrafao.phitag.infrastructure.rest;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.annotationtype.AnnotationTypeApplicationService;
import de.garrafao.phitag.application.annotationtype.data.AnnotationTypeDto;

@RestController
@RequestMapping(value = "/api/v1/annotationtype")
public class AnnotationTypeResource {

    private final AnnotationTypeApplicationService annotationTypeApplicationService;

    @Autowired
    public AnnotationTypeResource(AnnotationTypeApplicationService annotationTypeApplicationService) {
        this.annotationTypeApplicationService = annotationTypeApplicationService;
    }

    @RequestMapping()
    public Set<AnnotationTypeDto> all() {
        return this.annotationTypeApplicationService.getAnnotationTypeDtos();
    }
    
}
