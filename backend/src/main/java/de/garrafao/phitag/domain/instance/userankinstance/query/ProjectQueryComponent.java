package de.garrafao.phitag.domain.instance.userankinstance.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class ProjectQueryComponent implements QueryComponent {

    private final String project;

    public ProjectQueryComponent(String project) {
        this.project = project;
    }

}
