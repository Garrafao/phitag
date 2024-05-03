package de.garrafao.phitag.domain.instance.wssiminstance;

import java.util.Arrays;
import java.util.List;

import javax.persistence.*;

import org.apache.commons.lang3.Validate;

import de.garrafao.phitag.domain.instance.IInstance;
import de.garrafao.phitag.domain.instance.wssimtag.WSSIMTag;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.phitagdata.usage.Usage;
import lombok.Getter;

@Entity
@Table(name = "phitagwssiminstance")
@Getter
public class WSSIMInstance implements IInstance {
    
    @EmbeddedId
    private WSSIMInstanceId id;

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
        @JoinColumn(name = "phitagusage_projectname",  referencedColumnName = "projectname"),
        @JoinColumn(name = "phitagusage_ownername", referencedColumnName = "ownername") 
    })
    private Usage usage;

    @ManyToOne
    @JoinColumns({ 
        @JoinColumn(name = "phitagwssimtag_tagid", referencedColumnName = "tagid"),
        @JoinColumn(name = "phitagwssimtag_phasename",  referencedColumnName = "phasename"),
        @JoinColumn(name = "phitagwssimtag_projectname",  referencedColumnName = "projectname"),
        @JoinColumn(name = "phitagwssimtag_ownername", referencedColumnName = "ownername") 
    })
    private WSSIMTag wssimtag;

    @Column(name = "label_set", nullable = false)
    private String labelSet;

    @Column(name = "non_label", nullable = false)
    private String nonLabel;

    WSSIMInstance() {
    }

    public WSSIMInstance(final String instanceId, final Phase phase, final Usage usage, final WSSIMTag wssimtag,
            final String labelSet, final String nonLabel) {
        Validate.notNull(instanceId);
        Validate.notNull(phase);
        Validate.notNull(usage);
        Validate.notNull(wssimtag);

        this.id = new WSSIMInstanceId(instanceId, phase.getId());
        this.phase = phase;
        this.usage = usage;
        this.wssimtag = wssimtag;
        this.labelSet = labelSet;
        this.nonLabel = nonLabel;
    }


    public List<String> getLabelSet() {
        return Arrays.asList(labelSet.split(","));
    }

    @Override
    public String toString() {
        return String.format("WSSIMInstance [id=%s, phase=%s, usage=%s, wssimtag=%s, labelSet=%s, nonLabel=%s]", id, phase,
                usage, wssimtag, labelSet, nonLabel);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        WSSIMInstance other = (WSSIMInstance) obj;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
