package de.garrafao.phitag.domain.instance.wssimtag;

import javax.persistence.*;

import org.apache.commons.lang3.Validate;

import de.garrafao.phitag.domain.instance.IInstance;
import de.garrafao.phitag.domain.phase.Phase;
import lombok.Getter;

@Entity
@Table(name = "phitagwssimtag")
@Getter
public class WSSIMTag implements IInstance {

    @EmbeddedId
    private WSSIMTagId id;

    @MapsId("phaseid")
    @ManyToOne
    @JoinColumns({ 
        @JoinColumn(name = "phasename", referencedColumnName = "name"),
        @JoinColumn(name = "projectname", referencedColumnName = "projectname"),
        @JoinColumn(name = "ownername", referencedColumnName = "ownername") 
    })
    private Phase phase;

    @Column(name = "definition", nullable = false)
    private String definition;

    @Column(name = "lemma", nullable = false)
    private String lemma;

    WSSIMTag() {
    }

    public WSSIMTag(final String instanceId, final Phase phase, final String definition, final String lemma) {
        Validate.notNull(instanceId);
        Validate.notNull(phase);
        Validate.notNull(definition);
        Validate.notNull(lemma);

        this.id = new WSSIMTagId(instanceId, phase.getId());
        this.phase = phase;
        this.definition = definition;
        this.lemma = lemma;
    }
    
    @Override
    public String toString() {
        return String.format("WSSIMTag [id=%s, phase=%s, definition=%s, lemma=%s]", id, phase, definition, lemma);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final WSSIMTag other = (WSSIMTag) obj;
        return id.equals(other.id);
    }
    
}
