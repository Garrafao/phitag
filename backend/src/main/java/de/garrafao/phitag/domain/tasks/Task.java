package de.garrafao.phitag.domain.tasks;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.status.Status;
import de.garrafao.phitag.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "phitagtask")
@Getter
public class Task {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;    

    @ManyToOne
    @JoinColumn(name = "bot", referencedColumnName = "username")
    private User bot;


    @ManyToOne
    @JoinColumn(name = "taskowner", referencedColumnName = "username")
    private User taskowner;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "target_phasename", referencedColumnName = "name"),
        @JoinColumn(name = "target_projectname", referencedColumnName = "projectname"),
        @JoinColumn(name = "target_ownername", referencedColumnName = "ownername") 
    })
    private Phase phase;

    @Setter
    @ManyToOne
    @JoinColumn(name = "task_status", nullable = false)
    private Status status;

    Task() {
    }

    public Task(final User bot, final User taskowner, final Phase phase, final Status status) {
        Validate.notNull(bot);
        Validate.notNull(taskowner);
        Validate.notNull(phase);
        Validate.notNull(status);

        this.bot = bot;
        this.taskowner = taskowner;
        this.phase = phase;
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Task[id=%d, bot=%s, taskowner=%s, phase=%s, status=%s]", id, bot, taskowner, phase, status);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object == this) {
            return true;
        }
        if (!(object instanceof Task)) {
            return false;
        }
        Task other = (Task) object;
        return id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }


    
}
