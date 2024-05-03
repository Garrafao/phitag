package de.garrafao.phitag.domain.visibility;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Getter;

@Entity
@Table(name = "phitagvisibility")
@Getter
public class Visibility implements Serializable {

    @Id
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "visiblename", unique = true, nullable = false)
    private String visiblename;

    Visibility() {
    }

    Visibility(final String name) {
        this.name = name;
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
        if (!(object instanceof Visibility)) {
            return false;
        }
        Visibility other = (Visibility) object;
        return name.equals(other.getName());
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
    
}
