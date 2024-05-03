package de.garrafao.phitag.domain.instance.userankinstance;

import de.garrafao.phitag.domain.instance.IInstanceId;
import de.garrafao.phitag.domain.phase.PhaseId;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class UseRankInstanceId implements Serializable, IInstanceId {

    @Column(name = "instanceid")
    private  String instanceid;

    private PhaseId phaseid;

    public UseRankInstanceId() {
    }

    public UseRankInstanceId(String instanceid, PhaseId phaseid) {
        this.instanceid = instanceid;
        this.phaseid = phaseid;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        UseRankInstanceId other = (UseRankInstanceId) obj;
        return instanceid.equals(other.instanceid) && phaseid.equals(other.phaseid);
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
    public String toString() {
        return String.format("UseRankInstanceId[instanceId='%s', phaseId='%s']", instanceid, phaseid);
    }

}
