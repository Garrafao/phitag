package de.garrafao.phitag.application.annotationtype.data;

import de.garrafao.phitag.domain.annotationtype.AnnotationType;
import lombok.Getter;

@Getter
public class AnnotationTypeDto {

    private final String name;
    private final String visiblename;

    private AnnotationTypeDto(final String name, final String visiblename) {
        this.name = name;
        this.visiblename = visiblename;
    }

    public static AnnotationTypeDto from(AnnotationType annotationType) {
        return new AnnotationTypeDto(annotationType.getName(), annotationType.getVisiblename());
    }
    
}
