package de.garrafao.phitag.computationalannotator.common.model.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "phitag_openaimodel")
@Getter
public class OpenAIModel {
    
    @Id
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "visiblename", unique = true, nullable = false)
    private String visiblename;

    @Column(name = "active", nullable = false)
    private Boolean active;

    OpenAIModel() {
    }

    OpenAIModel(final String name, final String visiblename, final Boolean active) {
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
        if (!(object instanceof OpenAIModel)) {
            return false;
        }
        OpenAIModel other = (OpenAIModel) object;
        return name.equals(other.getName());
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
