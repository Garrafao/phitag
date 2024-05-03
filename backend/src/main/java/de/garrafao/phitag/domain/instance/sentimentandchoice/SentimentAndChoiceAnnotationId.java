package de.garrafao.phitag.domain.instance.sentimentandchoice;

import de.garrafao.phitag.domain.instance.IInstanceId;
import de.garrafao.phitag.domain.phase.PhaseId;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class SentimentAndChoiceAnnotationId implements Serializable, IInstanceId {
    
    @Column(name = "instanceid")
    private String instanceid;

    private PhaseId phaseid;

    public SentimentAndChoiceAnnotationId() {
    }

    public SentimentAndChoiceAnnotationId(final String instanceId, final PhaseId phaseid) {
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
        SentimentAndChoiceAnnotationId other = (SentimentAndChoiceAnnotationId) obj;
        return instanceid.equals(other.instanceid) && phaseid.equals(other.phaseid);
    }

    @Override
    public String toString() {
        return String.format("SentimentAndChoiceInstanceRepository[instanceId='%s', phaseId='%s']", instanceid, phaseid);
    }
    
}
