package de.garrafao.phitag.domain.annotator.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class AnnotationTypeQueryComponent implements QueryComponent {
  
    private final String name;

    public AnnotationTypeQueryComponent(final String name) {
        this.name = name;
    }
}
