package de.garrafao.phitag.domain.tutorial.usepair;

import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.instance.usepairinstance.UsePairInstance;
import de.garrafao.phitag.domain.judgement.IJudgement;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Validate;

import javax.persistence.*;

@Entity
@Table(name = "phitagusepairtutorialjudgement")
@Getter
public class UsePairTutorialJudgement implements IJudgement {

    @Setter
    @EmbeddedId
    private UsePairTutorialJudgementId id;

    @MapsId("annotatorid")
    @ManyToOne
    private Annotator annotator;

    @MapsId("instanceid")
    @ManyToOne
    private UsePairInstance instance;

    @Setter
    @Column(name = "label", nullable = false)
    private String label;

    @Setter
    @Column(name = "comment", nullable = false)
    private String comment;

    UsePairTutorialJudgement() {
    }

    public UsePairTutorialJudgement(UsePairInstance usePairInstance, Annotator annotator, String label, String comment) {
        Validate.notNull(usePairInstance, "instanceData must not be null");
        Validate.notNull(annotator, "annotator must not be null");
        Validate.notNull(label, "label must not be null");
        Validate.notNull(comment, "comment must not be null");

        this.id = new UsePairTutorialJudgementId(usePairInstance.getId(), annotator.getId());

        this.instance = usePairInstance;
        this.annotator = annotator;

        this.label = label;
        this.comment = comment;
    }

    public UsePairInstance getUsePairInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "UsePairTutorialJudgement [id=" + id + ", label=" + label + ", comment=" + comment + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UsePairTutorialJudgement other = (UsePairTutorialJudgement) obj;
        return this.id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

}
