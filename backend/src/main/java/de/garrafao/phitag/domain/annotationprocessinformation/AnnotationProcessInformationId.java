package de.garrafao.phitag.domain.annotationprocessinformation;

import java.io.Serializable;

import javax.persistence.Embeddable;

import de.garrafao.phitag.domain.annotator.AnnotatorId;
import de.garrafao.phitag.domain.phase.PhaseId;
import lombok.Getter;

@Getter
@Embeddable
public class AnnotationProcessInformationId implements Serializable {

    private AnnotatorId annotatorid;

    private PhaseId phaseid;

    public AnnotationProcessInformationId() {
    }

    public AnnotationProcessInformationId(final AnnotatorId annotatorid, final PhaseId phaseid) {
        this.annotatorid = annotatorid;
        this.phaseid = phaseid;
    }

    @Override
    public String toString() {
        return String.format("SamplingOrderId [annotatorid=%s, phaseid=%s]", annotatorid, phaseid);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((annotatorid == null) ? 0 : annotatorid.hashCode());
        result = prime * result + ((phaseid == null) ? 0 : phaseid.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AnnotationProcessInformationId other = (AnnotationProcessInformationId) obj;
        return annotatorid.equals(other.annotatorid) && phaseid.equals(other.phaseid);
    }
    
}
