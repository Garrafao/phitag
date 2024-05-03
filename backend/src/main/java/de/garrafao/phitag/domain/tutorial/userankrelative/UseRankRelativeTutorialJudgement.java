package de.garrafao.phitag.domain.tutorial.userankrelative;

import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.instance.userankrelative.UseRankRelativeInstance;
import de.garrafao.phitag.domain.judgement.IJudgement;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Validate;

import javax.persistence.*;

@Entity
@Table(name = "phitaguserankrelativetutorialjudgement")
@Getter
public class UseRankRelativeTutorialJudgement implements IJudgement {

    @Setter
    @EmbeddedId
    private UseRankRelativeTutorialJudgementId id;

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

    UseRankRelativeTutorialJudgement() {
    }

    public UseRankRelativeTutorialJudgement(UseRankRelativeInstance useRankRelativeInstance, Annotator annotator, String label, String comment) {
        Validate.notNull(useRankRelativeInstance, "instanceData must not be null");
        Validate.notNull(annotator, "annotator must not be null");
        Validate.notNull(label, "label must not be null");
        Validate.notNull(comment, "comment must not be null");

        this.id = new UseRankRelativeTutorialJudgementId(useRankRelativeInstance.getId(), annotator.getId());

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
        return "UseRankRelativeTutorialJudgement [id=" + id + ", label=" + label + ", comment=" + comment + "]";
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
        UseRankRelativeTutorialJudgement other = (UseRankRelativeTutorialJudgement) obj;
        return this.id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

}
