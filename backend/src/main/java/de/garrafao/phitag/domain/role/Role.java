package de.garrafao.phitag.domain.role;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Getter;

@Entity
@Table(name = "phitagrole")
@Getter
public class Role implements Serializable {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "visiblename", unique = true, nullable = false)
    private String visiblename;
    
    Role() {
    }

    Role(final String name) {
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
        if (!(object instanceof Role)) {
            return false;
        }
        Role other = (Role) object;
        return name.equals(other.getName());
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
