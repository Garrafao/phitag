package de.garrafao.phitag.domain.annotationtype;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name = "phitagannotationtype")
@Getter
public class AnnotationType {
    
    @Id
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "visiblename", unique = true, nullable = false)
    private String visiblename;

    @Column(name = "active", nullable = false)
    private Boolean active;

    AnnotationType() {
    }

    AnnotationType(final String name, final String visiblename, final Boolean active) {
        this.name = name;
        this.visiblename = visiblename;
        this.active = active;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object == this) {
            return true;
        }
        if (!(object instanceof AnnotationType)) {
            return false;
        }
        AnnotationType other = (AnnotationType) object;
        return name.equals(other.getName());
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
