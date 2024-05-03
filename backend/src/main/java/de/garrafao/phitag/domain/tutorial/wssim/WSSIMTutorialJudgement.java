package de.garrafao.phitag.domain.tutorial.wssim;

import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.instance.wssiminstance.WSSIMInstance;
import de.garrafao.phitag.domain.judgement.IJudgement;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Validate;

import javax.persistence.*;

@Entity
@Table(name = "phitagwssimtutorialjudgement")
@Getter
public class WSSIMTutorialJudgement implements IJudgement {

    @Setter
    @EmbeddedId
    private WSSIMTutorialJudgementId id;

    @MapsId("annotatorid")
    @ManyToOne
    private Annotator annotator;

    @MapsId("instanceid")
    @ManyToOne
    private WSSIMInstance instance;

    @Setter
    @Column(name = "label", nullable = false)
    private String label;

    @Setter
    @Column(name = "comment", nullable = false)
    private String comment;

    WSSIMTutorialJudgement() {
    }

    public WSSIMTutorialJudgement(final WSSIMInstance instance, final Annotator annotator, final String label, final String comment) {
        Validate.notNull(instance, "instanceData must not be null");
        Validate.notNull(annotator, "annotator must not be null");
        Validate.notNull(label, "label must not be null");
        Validate.notNull(comment, "comment must not be null");

        this.id = new WSSIMTutorialJudgementId(instance.getId(), annotator.getId());

        this.instance = instance;
        this.annotator = annotator;

        this.label = label;
        this.comment = comment;
    }

    public WSSIMInstance getWSSIMInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return String.format("WSSIMTutorialJudgement [id=%s, annotator=%s, wssimInstance=%s, label=%s, comment=%s]", id, annotator, instance, label, comment);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        WSSIMTutorialJudgement other = (WSSIMTutorialJudgement) obj;
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    
}
