package de.garrafao.phitag.domain.tutorial.lexsub;

import de.garrafao.phitag.domain.annotator.AnnotatorId;
import de.garrafao.phitag.domain.instance.lexsub.LexSubInstanceId;
import de.garrafao.phitag.domain.judgement.IJudgementId;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Data
@Embeddable
public class LexSubTutorialJudgementId implements Serializable, IJudgementId {

    @Column(name = "uuid")
    private String uuid;

    private AnnotatorId annotatorid;

    private LexSubInstanceId instanceid;

    public LexSubTutorialJudgementId() {
    }

    public LexSubTutorialJudgementId(final LexSubInstanceId instanceid, final AnnotatorId annotatorid) {
        this.uuid = UUID.randomUUID().toString();
        this.instanceid = instanceid;
        this.annotatorid = annotatorid;
    }

    @Override
    public String getUUID() {
        return uuid;
    }

    @Override
    public String toString() {
        return String.format("LexSubTutorialJudgementId [instanceid=%s, annotatorid=%s]", instanceid,
                annotatorid);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((annotatorid == null) ? 0 : annotatorid.hashCode());
        result = prime * result + ((instanceid == null) ? 0 : instanceid.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        LexSubTutorialJudgementId other = (LexSubTutorialJudgementId) obj;
        return uuid.equals(other.uuid) && annotatorid.equals(other.annotatorid)
                && instanceid.equals(other.instanceid);
    }

}
