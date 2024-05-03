package de.garrafao.phitag.domain.statistic.tutorialannotationmeasurehistory;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.phase.Phase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tutorialannotationmeasurehistory")
@Getter
@EqualsAndHashCode
public class TutorialAnnotationMeasureHistory {

    @Setter
    @EmbeddedId
    private TutorialAnnotationMeasureHistoryId id;

    @MapsId("annotatorid")
    @ManyToOne
    private Annotator annotator;

    @MapsId("phaseid")
    @ManyToOne
    private Phase phase;

    @EqualsAndHashCode.Exclude
    @Column(name = "measure", nullable = false)
    private Double measure;

    @EqualsAndHashCode.Exclude
    @Column(name = "passed", nullable = false)
    private Boolean passed;

    TutorialAnnotationMeasureHistory() {
    }

    public TutorialAnnotationMeasureHistory(final Phase phase, final Annotator annotator, final Double measure,
            final Boolean passed) {
        this.id = new TutorialAnnotationMeasureHistoryId(phase.getId(), annotator.getId());

        this.phase = phase;
        this.annotator = annotator;

        this.measure = measure;
        this.passed = passed;
    }

}