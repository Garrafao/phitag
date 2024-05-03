package de.garrafao.phitag.domain.instance.wssimtag;

import java.io.Serializable;

import javax.persistence.*;

import de.garrafao.phitag.domain.instance.IInstanceId;
import de.garrafao.phitag.domain.phase.PhaseId;
import lombok.Data;

@Data
@Embeddable
public class WSSIMTagId implements Serializable, IInstanceId {

    @Column(name = "tagid")
    private String tagid;

    private PhaseId phaseid;

    public WSSIMTagId() {
    }

    public WSSIMTagId(final String tagId, final PhaseId phaseid) {
        this.tagid = tagId;
        this.phaseid = phaseid;
    }

    @Override
    public String getInstanceid() {
        return tagid;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tagid == null) ? 0 : tagid.hashCode());
        result = prime * result + ((phaseid == null) ? 0 : phaseid.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        WSSIMTagId other = (WSSIMTagId) obj;
        return tagid.equals(other.tagid) && phaseid.equals(other.phaseid);
    }

    @Override
    public String toString() {
        return String.format("WSSIMTagId[tagId='%s', phaseId='%s']", tagid, phaseid);
    }

}
