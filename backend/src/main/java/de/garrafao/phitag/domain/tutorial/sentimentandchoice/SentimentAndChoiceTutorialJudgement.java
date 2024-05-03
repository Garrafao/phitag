package de.garrafao.phitag.domain.tutorial.sentimentandchoice;

import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.instance.sentimentandchoice.SentimentAndChoiceInstance;
import de.garrafao.phitag.domain.judgement.IJudgement;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Validate;

import javax.persistence.*;

@Entity
@Table(name = "phitagsentimentandchoicetutorialjudgement")
@Getter
public class SentimentAndChoiceTutorialJudgement implements IJudgement {

    @EmbeddedId
    private SentimentAndChoiceTutorialJudgementId id;

    @MapsId("annotatorid")
    @ManyToOne
    private Annotator annotator;

    @MapsId("instanceid")
    @ManyToOne
    private SentimentAndChoiceInstance instance;

    @Setter
    @Column(name = "label", nullable = false)
    private String label;

    @Setter
    @Column(name = "comment", nullable = false)
    private String comment;

    public SentimentAndChoiceTutorialJudgement() {
    }

    public SentimentAndChoiceTutorialJudgement(final SentimentAndChoiceInstance instance, final Annotator annotator, final String label, final String comment) {
        Validate.notNull(instance, "instance must not be null");
        Validate.notNull(annotator, "annotator must not be null");
        // Validate.notBlank(label, "label must not be null");

        this.id = new SentimentAndChoiceTutorialJudgementId(instance.getId(), annotator.getId());

        this.instance = instance;
        this.annotator = annotator;

        this.label = label;
        this.comment = comment;
    }
    
}
