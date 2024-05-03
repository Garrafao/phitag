package de.garrafao.phitag.application.project.data;
import lombok.Getter;
import org.apache.commons.lang3.Validate;

@Getter
public class UpdateProjectCommand {
    private String newdescription;
    private String newname;
    private final String visibility;

    private final String language;

    private  final Boolean active;


    public UpdateProjectCommand(final String newdescription, final String newname, final String language, final String visibility, final Boolean active) {



        this.newname = newname;
        this.language = language;
        this.newdescription = newdescription;
        this.visibility = visibility;
        this.active = active;

    }
}
