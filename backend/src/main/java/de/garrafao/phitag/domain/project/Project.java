package de.garrafao.phitag.domain.project;

import javax.persistence.*;

import lombok.Setter;
import org.apache.commons.lang3.Validate;

import de.garrafao.phitag.domain.language.Language;
import de.garrafao.phitag.domain.user.User;
import de.garrafao.phitag.domain.visibility.Visibility;
import lombok.Getter;

@Entity
@Table(name = "phitagproject")
@Getter
@Setter
public class Project {


    // IDs of the project and the owner as composite key
    @EmbeddedId
    private ProjectId id;

    @MapsId("ownername")
    @ManyToOne
    @JoinColumn(name = "ownername", referencedColumnName = "username")
    private User owner;

    @Column(name = "displayname", unique = true, nullable = false)
    private String displayname;

    @Column(name = "active", nullable = false)
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "phitagvisibility_name")
    private Visibility visibility;

    @ManyToOne
    @JoinColumn(name = "phitaglanguage_name", nullable = false)
    private Language language;

    @Column(name = "description")
    private String description;

    Project() {
    }

    public Project(final String name, final User owner, final Visibility visibility, final Language language, final String description) {
        // Validate that name only contains letters, numbers and dashes
        Validate.notEmpty(name);
        Validate.matchesPattern(name, "^[a-zA-Z0-9-]+$");

        Validate.notNull(owner);
        Validate.notNull(visibility);
        Validate.notNull(language);
        Validate.notNull(description);

        this.id = new ProjectId(name, owner.getUsername());
        this.owner = owner;

        this.displayname = name;
        this.active = true;
        this.visibility = visibility;

        this.language = language;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Project[id=%s, active='%b']", this.id, this.active);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object == this) {
            return true;
        }
        if (!(object instanceof Project)) {
            return false;
        }
        Project project = (Project) object;
        return this.id.equals(project.getId());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }



}
