package de.garrafao.phitag.domain.judgement.userankrelativejudgement;

import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.instance.userankrelative.UseRankRelativeInstance;
import de.garrafao.phitag.domain.judgement.IJudgement;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Validate;

import javax.persistence.*;

@Entity
@Table(name = "phitaguserankrelativejudgement")
@Getter
public class UseRankRelativeJudgement implements IJudgement {

    @Setter
    @EmbeddedId
    private UseRankRelativeJudgementId id;

    @MapsId("annotatorid")
    @ManyToOne
    private Annotator annotator;

    @MapsId("instanceid")
    @ManyToOne
    private UseRankRelativeInstance instance;

    @Setter
    @Column(name = "rank", nullable = false)
    private String  label;

    @Setter
    @Column(name = "comment", nullable = false)
    private String comment;

    UseRankRelativeJudgement() {
    }

    public UseRankRelativeJudgement(UseRankRelativeInstance useRankRelativeInstance, Annotator annotator, String label, String comment) {
        Validate.notNull(useRankRelativeInstance, "instanceData must not be null");
        Validate.notNull(annotator, "annotator must not be null");
        Validate.notNull(label, "label must not be null");
        Validate.notNull(comment, "comment must not be null");

        this.id = new UseRankRelativeJudgementId(useRankRelativeInstance.getId(), annotator.getId());

        this.instance = useRankRelativeInstance;
        this.annotator = annotator;

        this.label = label;
        this.comment = comment;
    }

    public UseRankRelativeInstance getUseRankRelativeInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "UseRankRelativeJudgement [id=" + id + ", label=" + label + ", comment=" + comment + "]";
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
        UseRankRelativeJudgement other = (UseRankRelativeJudgement) obj;
        return this.id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

}
