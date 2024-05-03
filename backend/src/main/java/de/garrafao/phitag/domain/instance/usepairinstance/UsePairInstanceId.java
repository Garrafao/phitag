package de.garrafao.phitag.domain.instance.usepairinstance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import de.garrafao.phitag.domain.instance.IInstanceId;
import de.garrafao.phitag.domain.phase.PhaseId;
import lombok.Data;

@Data
@Embeddable
public class UsePairInstanceId implements Serializable, IInstanceId {
    
    
    @Column(name = "instanceid")
    private String instanceid;

    private PhaseId phaseid;

    public UsePairInstanceId() {
    }

    public UsePairInstanceId(final String instanceId, final PhaseId phaseid) {
        this.instanceid = instanceId;
        this.phaseid = phaseid;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((instanceid == null) ? 0 : instanceid.hashCode());
        result = prime * result + ((phaseid == null) ? 0 : phaseid.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        UsePairInstanceId other = (UsePairInstanceId) obj;
        return instanceid.equals(other.instanceid) && phaseid.equals(other.phaseid);
    }

    @Override
    public String toString() {
        return String.format("UsePairInstanceId[instanceId='%s', phaseId='%s']", instanceid, phaseid);
    }
}
