package de.garrafao.phitag.domain.annotator;

import java.io.Serializable;

import javax.persistence.Embeddable;

import de.garrafao.phitag.domain.project.ProjectId;
import lombok.Data;

@Data
@Embeddable
public class AnnotatorId implements Serializable {
    
    private String username;

    private ProjectId projectid;

    public AnnotatorId() {
    }

    public AnnotatorId(final String username, final ProjectId projectid) {
        this.username = username;
        this.projectid = projectid;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((projectid == null) ? 0 : projectid.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        AnnotatorId other = (AnnotatorId) obj;
        return projectid.equals(other.projectid) && username.equals(other.username);
    }
}
