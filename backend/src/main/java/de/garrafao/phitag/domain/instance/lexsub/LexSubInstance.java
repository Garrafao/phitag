package de.garrafao.phitag.domain.instance.lexsub;

import java.util.Arrays;
import java.util.List;

import javax.persistence.*;

import org.apache.commons.lang3.Validate;

import de.garrafao.phitag.domain.instance.IInstance;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.phitagdata.usage.Usage;
import lombok.Getter;

@Entity
@Table(name = "phitaglexsubinstance")
@Getter
public class LexSubInstance implements IInstance {

    @EmbeddedId
    private LexSubInstanceId id;

    @MapsId("phaseid")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "phasename", referencedColumnName = "name"),
            @JoinColumn(name = "projectname", referencedColumnName = "projectname"),
            @JoinColumn(name = "ownername", referencedColumnName = "ownername")
    })
    private Phase phase;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "phitagusage_dataid", referencedColumnName = "dataid"),
            @JoinColumn(name = "phitagusage_projectname", referencedColumnName = "projectname"),
            @JoinColumn(name = "phitagusage_ownername", referencedColumnName = "ownername")
    })
    private Usage usage;

    @Column(name = "label_set", nullable = false)
    private String labelSet;

    @Column(name = "non_label", nullable = false)
    private String nonLabel;

    LexSubInstance() {
    }

    public LexSubInstance(
            final String instanceId, final Phase phase,
            final Usage usage, final String labelSet, final String nonLabel) {
        Validate.notNull(instanceId);
        Validate.notNull(phase);
        Validate.notNull(usage);

        this.id = new LexSubInstanceId(instanceId, phase.getId());
        this.phase = phase;
        this.usage = usage;
        this.labelSet = labelSet;
        this.nonLabel = nonLabel;
    }

    public List<String> getLabelSet() {
        return Arrays.asList(labelSet.split(","));
    }

    @Override
    public String toString() {
        return String.format("InstanceData [id=%s]", id);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final LexSubInstance other = (LexSubInstance) obj;
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

}
