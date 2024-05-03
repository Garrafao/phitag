package de.garrafao.phitag.domain.judgement.userankpairjudgement;

import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.instance.userankpairinstances.UseRankPairInstance;
import de.garrafao.phitag.domain.judgement.IJudgement;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Validate;

import javax.persistence.*;

@Entity
@Table(name = "phitaguserankpairjudgement")
@Getter
public class UseRankPairJudgement implements IJudgement {

    @Setter
    @EmbeddedId
    private UseRankPairJudgementId id;

    @MapsId("annotatorid")
    @ManyToOne
    private Annotator annotator;

    @MapsId("instanceid")
    @ManyToOne
    private UseRankPairInstance instance;

    @Setter
    @Column(name = "rank", nullable = false)
    private String  label;

    @Setter
    @Column(name = "comment", nullable = false)
    private String comment;

    UseRankPairJudgement() {
    }

    public UseRankPairJudgement(UseRankPairInstance instance, Annotator annotator, String label, String comment) {
        Validate.notNull(instance, "instanceData must not be null");
        Validate.notNull(annotator, "annotator must not be null");
        Validate.notNull(label, "label must not be null");
        Validate.notNull(comment, "comment must not be null");

        this.id = new UseRankPairJudgementId(instance.getId(), annotator.getId());

        this.instance = instance;
        this.annotator = annotator;

        this.label = label;
        this.comment = comment;
    }



    public UseRankPairInstance getUseRankPairInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "UseRankPairJudgement [id=" + id + ", label=" + label + ", comment=" + comment + "]";
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
        UseRankPairJudgement other = (UseRankPairJudgement) obj;
        return this.id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

}
