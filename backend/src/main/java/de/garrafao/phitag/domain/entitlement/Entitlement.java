package de.garrafao.phitag.domain.entitlement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name = "phitagentitlement")
@Getter
public class Entitlement {

    @Id
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "visiblename", unique = true, nullable = false)
    private String visiblename;

    Entitlement() {
    }

    Entitlement(final String name) {
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
        if (!(object instanceof Entitlement)) {
            return false;
        }
        Entitlement other = (Entitlement) object;
        return name.equals(other.getName());
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

}
