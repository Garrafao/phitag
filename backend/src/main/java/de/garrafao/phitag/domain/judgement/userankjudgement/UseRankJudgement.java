package de.garrafao.phitag.domain.judgement.userankjudgement;

import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.instance.userankinstance.UseRankInstance;
import de.garrafao.phitag.domain.judgement.IJudgement;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Validate;

import javax.persistence.*;

@Entity
@Table(name = "phitaguserankjudgement")
@Getter
public class UseRankJudgement implements IJudgement {

    @Setter
    @EmbeddedId
    private UseRankJudgementId id;

    @MapsId("annotatorid")
    @ManyToOne
    private Annotator annotator;

    @MapsId("instanceid")
    @ManyToOne
    private UseRankInstance instance;

    @Setter
    @Column(name = "rank", nullable = false)
    private String  label;

    @Setter
    @Column(name = "comment", nullable = false)
    private String comment;

    public UseRankJudgement() {
    }

    public UseRankJudgement(UseRankInstance useRankInstance, Annotator annotator, String label, String comment) {
        Validate.notNull(useRankInstance, "instanceData must not be null");
        Validate.notNull(annotator, "annotator must not be null");
        Validate.notNull(label, "label must not be null");
        Validate.notNull(comment, "comment must not be null");

        this.id = new UseRankJudgementId(useRankInstance.getId(), annotator.getId());

        this.instance = useRankInstance;
        this.annotator = annotator;

        this.label = label;
        this.comment = comment;
    }

    public UseRankInstance getUseRankInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "UseRankJudgement [id=" + id + ", label=" + label + ", comment=" + comment + "]";
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
        UseRankJudgement other = (UseRankJudgement) obj;
        return this.id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

}
