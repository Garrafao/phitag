package de.garrafao.phitag.domain.joblisting;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
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

import de.garrafao.phitag.domain.project.Project;
import de.garrafao.phitag.domain.user.User;
import lombok.Getter;

@Entity
@Table(name = "phitagjoblisting")
@Getter
public class Joblisting {
    
    @EmbeddedId
    private JoblistingId id;
    
    @MapsId("projectid")
    @ManyToOne
    @JoinColumns({ 
        @JoinColumn(name = "projectname", referencedColumnName = "name"),
        @JoinColumn(name = "ownername", referencedColumnName = "ownername") 
    })
    private Project project;

    @Column(name = "displayname", unique = true, nullable = false)
    private String displayname;

    @Column(name = "phasename", unique = true, nullable = false)
    private String phase;

    @Column(name = "open")
    private boolean open;

    @Column(name = "active")
    private boolean active;

    @ManyToMany
    @JoinTable(
        name = "phitagjoblisting_phitaguser",
        joinColumns = {
            @JoinColumn(name = "phitagjoblisting_name", referencedColumnName = "name"),
            @JoinColumn(name = "phitagjoblisting_projectname", referencedColumnName = "projectname"),
            @JoinColumn(name = "phitagjoblisting_ownername", referencedColumnName = "ownername")
        },
        inverseJoinColumns = @JoinColumn(name = "phitaguser_username", referencedColumnName = "username")
    )
    private List<User> waitinglist;

    @Column(name = "description")
    private String description;

    Joblisting() {}

    public Joblisting(final String name, final Project project, final String phase, final boolean open, final String description) {
        Validate.matchesPattern(name, "^[a-zA-Z0-9-]+$");
        
        this.id = new JoblistingId(name, project.getId());
        this.project = project;
        this.displayname = name;
        this.phase = phase;
        this.open = open;
        this.active = true;
        this.waitinglist = new ArrayList<>();
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Joblisting[id=%s, open='%s', active='%s', waitinglist='%s', description='%s']", id, open, active, waitinglist, description);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Joblisting)) {
            return false;
        }
        Joblisting other = (Joblisting) obj;
        return this.id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
