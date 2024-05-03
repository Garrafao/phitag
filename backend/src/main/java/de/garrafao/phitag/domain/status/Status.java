package de.garrafao.phitag.domain.status;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name = "phitagstatus")
@Getter
public class Status {

    @Id
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "visiblename", unique = true, nullable = false)
    private String visiblename;

    Status() {
    }

    Status(final String name, final String visiblename) {
        this.name = name;
        this.visiblename = visiblename;
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
        if (!(object instanceof Status)) {
            return false;
        }
        Status other = (Status) object;
        return name.equals(other.getName());
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
