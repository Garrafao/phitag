package de.garrafao.phitag.domain.judgement.spanjudgement;

import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.instance.spaninstance.SpanInstance;
import de.garrafao.phitag.domain.judgement.IJudgement;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Validate;

import javax.persistence.*;

@Entity
@Table(name = "phitagspanjudgement")
@Getter
public class SpanJudgement implements IJudgement {

    @EmbeddedId
    private SpanJudgementId id;

    @MapsId("annotatorid")
    @ManyToOne
    private Annotator annotator;

    @MapsId("instanceid")
    @ManyToOne
    private SpanInstance instance;

    @Setter
    @Column(name = "label", nullable = false)
    private String label;

    @Setter
    @Column(name = "comment", nullable = false)
    private String comment;

    public SpanJudgement() {
    }

    public SpanJudgement(final SpanInstance instance, final Annotator annotator, final String label, final String comment) {
        Validate.notNull(instance, "instance must not be null");
        Validate.notNull(annotator, "annotator must not be null");
        // Validate.notBlank(label, "label must not be null");

        this.id = new SpanJudgementId(instance.getId(), annotator.getId());

        this.instance = instance;
        this.annotator = annotator;

        this.label = label;
        this.comment = comment;
    }
    
}
