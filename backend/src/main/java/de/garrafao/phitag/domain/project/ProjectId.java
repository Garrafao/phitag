package de.garrafao.phitag.domain.project;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Embeddable
public class ProjectId implements Serializable {

    @Column(name = "name")
    private String name;

    private String ownername;

    public ProjectId() {
    }

    public ProjectId(final String name, final String ownername) {
        this.name = name;
        this.ownername = ownername;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((ownername == null) ? 0 : ownername.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        ProjectId other = (ProjectId) obj;
        return name.equals(other.name) && ownername.equals(other.ownername);
    }



    @Override
    public String toString() {
        return String.format("ProjectId[name='%s', ownerId='%s']", name, ownername);
    }
    
}
