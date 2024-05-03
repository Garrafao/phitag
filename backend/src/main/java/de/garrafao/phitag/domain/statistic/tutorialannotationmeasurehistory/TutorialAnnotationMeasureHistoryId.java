package de.garrafao.phitag.domain.statistic.tutorialannotationmeasurehistory;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import de.garrafao.phitag.domain.annotator.AnnotatorId;
import de.garrafao.phitag.domain.phase.PhaseId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Embeddable
@EqualsAndHashCode
@ToString
public class TutorialAnnotationMeasureHistoryId implements Serializable {

    @Column(name = "timestamp")
    private String uuid;

    private AnnotatorId annotatorid;

    private PhaseId phaseid;

    public TutorialAnnotationMeasureHistoryId() {
    }

    public TutorialAnnotationMeasureHistoryId(
            final PhaseId phaseid,
            final AnnotatorId annotatorid) {
        this.uuid = String.valueOf(System.currentTimeMillis());
        this.phaseid = phaseid;
        this.annotatorid = annotatorid;
    }

    
}