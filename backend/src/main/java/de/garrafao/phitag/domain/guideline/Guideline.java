package de.garrafao.phitag.domain.guideline;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import de.garrafao.phitag.domain.project.Project;
import lombok.Getter;

@Entity
@Table(name = "phitaguideline")
@Getter
public class Guideline {
    
    @EmbeddedId
    private GuidelineId id;

    @MapsId("projectid")
    @ManyToOne
    @JoinColumns({ 
        @JoinColumn(name = "projectname", referencedColumnName = "name"),
        @JoinColumn(name = "ownername", referencedColumnName = "ownername") 
    })
    private Project project;

    @Column(name = "content", nullable = false)
    private String content;

    Guideline() {
    }

    public Guideline(final String name, final Project project, final String content) {
        Validate.notBlank(name, "name must not be blank");        
        Validate.matchesPattern(name, "^[a-zA-Z0-9-]+$");

        Validate.notNull(project, "project must not be null");
        Validate.notBlank(content, "content must not be blank");
        
        this.id = new GuidelineId(name, project.getId());
        this.project = project;
        this.content = content;
    }

    @Override
    public String toString() {
        return String.format("Guidelines[id=%s']", id);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Guideline other = (Guideline) obj;
        return this.id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
