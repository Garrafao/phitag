package de.garrafao.phitag.domain.language;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Getter;

@Entity
@Table(name = "phitaglanguage")
@Getter
public class Language implements Serializable {

    @Id
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    Language() {
    }

    Language(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Language other = (Language) obj;
        return this.name.equals(other.getName());
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
