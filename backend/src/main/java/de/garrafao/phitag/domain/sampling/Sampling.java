package de.garrafao.phitag.domain.sampling;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "phitagsamplingstrategy")
@Getter
public class Sampling {
    
    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "visiblename", unique = true, nullable = false)
    private String visiblename;

    Sampling() {
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
        if (!(object instanceof Sampling)) {
            return false;
        }
        Sampling other = (Sampling) object;
        return name.equals(other.getName());
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
