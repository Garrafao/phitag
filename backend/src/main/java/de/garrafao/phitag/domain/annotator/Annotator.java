package de.garrafao.phitag.domain.annotator;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import de.garrafao.phitag.domain.entitlement.Entitlement;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.project.Project;
import de.garrafao.phitag.domain.user.User;
import lombok.Data;

@Entity
@Table(name = "phitagannotator")
@Data
public class Annotator {

    @EmbeddedId
    private AnnotatorId id;

    @MapsId("username")
    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    @MapsId("projectid")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "projectname", referencedColumnName = "name"),
            @JoinColumn(name = "ownername", referencedColumnName = "ownername")
    })
    private Project project;

    @ManyToOne
    @JoinColumn(name = "phitagentitlement_name", nullable = false)
    private Entitlement entitlement;

    @ManyToMany
    @JoinTable(name = "phitagannotator_phitagtutorial", joinColumns = {
            @JoinColumn(name = "phitagannotator_username", referencedColumnName = "username"),
            @JoinColumn(name = "phitagannotator_projectname", referencedColumnName = "projectname"),
            @JoinColumn(name = "phitagannotator_ownername", referencedColumnName = "ownername")
    }, inverseJoinColumns = {
            @JoinColumn(name = "phitagtutorial_phasename", referencedColumnName = "name"),
            @JoinColumn(name = "phitagtutorial_projectname", referencedColumnName = "projectname"),
            @JoinColumn(name = "phitagtutorial_ownername", referencedColumnName = "ownername")
    })
    private List<Phase> completedTutorials = new ArrayList<>();

    Annotator() {
    }

    public Annotator(final User user, final Project project, final Entitlement entitlement) {
        Validate.notNull(user);
        Validate.notNull(project);

        this.id = new AnnotatorId(user.getUsername(), project.getId());

        this.user = user;
        this.project = project;
        this.entitlement = entitlement;
    }

    public void addCompletedTutorial(final Phase phase) {
        Validate.notNull(phase);
        if (!completedTutorials.contains(phase)) {
            completedTutorials.add(phase);
        }
    }

    @Override
    public String toString() {
        return String.format("Annotator [id=%s, entitlement='%s']", id, entitlement);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object == this) {
            return true;
        }
        if (!(object instanceof Annotator)) {
            return false;
        }
        Annotator other = (Annotator) object;
        return this.id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

}
