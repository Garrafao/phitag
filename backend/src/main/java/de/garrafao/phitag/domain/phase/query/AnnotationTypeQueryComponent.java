package de.garrafao.phitag.domain.phase.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class AnnotationTypeQueryComponent implements QueryComponent {
    
    private final String annotationType;

    public AnnotationTypeQueryComponent(String annotationType) {
        this.annotationType = annotationType;
    }
    
}
