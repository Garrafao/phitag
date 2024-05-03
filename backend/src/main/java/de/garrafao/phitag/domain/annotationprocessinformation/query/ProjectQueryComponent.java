package de.garrafao.phitag.domain.annotationprocessinformation.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class ProjectQueryComponent implements QueryComponent {

    private final String project;

    public ProjectQueryComponent(final String project) {
        this.project = project;
    }
    
}
