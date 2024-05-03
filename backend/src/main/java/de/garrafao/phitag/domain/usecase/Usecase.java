package de.garrafao.phitag.domain.usecase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name = "phitagusecase")
@Getter
public class Usecase {
    
    @Id
    private String name;

    @Column(name = "visiblename", unique = true, nullable = false)
    private String visiblename;

    Usecase() {
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
        if (!(object instanceof Usecase)) {
            return false;
        }
        Usecase other = (Usecase) object;
        return name.equals(other.getName());
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
