package de.garrafao.phitag.application.project.data;

import org.apache.commons.lang3.Validate;

import lombok.Getter;

@Getter
public class CreateProjectCommand {
 
    private final String name;

    private final String visibility;
    
    private final String language;
    private final String description;

    public CreateProjectCommand(final String name, final String visibility, final String language, final String description) {
        Validate.notBlank(name, "name must not be blank");
        Validate.notBlank(visibility, "name must not be blank");
        Validate.notBlank(language, "language must not be blank");

        this.name = name;
        
        this.visibility = visibility;

        this.language = language;
        this.description = description;
    }

}
