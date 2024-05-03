package de.garrafao.phitag.domain.statistic.statistictask;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import de.garrafao.phitag.domain.status.Status;
import de.garrafao.phitag.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "phitagstatistictask")
@Getter
public class StatisticTask {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "bot", referencedColumnName = "username")
    private User bot;

    @Setter
    @ManyToOne
    @JoinColumn(name = "task_status", nullable = false)
    private Status status;

    @Column(name = "target_ownername")
    private String targetOwnername;
    
    @Column(name = "target_projectname")
    private String targetProjectname;

    @Column(name = "target_phasename")
    private String targetPhasename;

    @Column(name = "target_annotatorname")
    private String targetAnnotatorname;


    StatisticTask() {
    }

    public StatisticTask(
            final User bot,
            final Status status,
            final String targetOwnername) {

        Validate.notNull(bot);
        Validate.notNull(status);

        this.bot = bot;
        this.status = status;

        this.targetPhasename = "";
        this.targetProjectname = "";
        this.targetOwnername = targetOwnername;
    }

    public StatisticTask(
            final User bot,
            final Status status,
            final String targetProjectname,
            final String targetOwnername) {

        Validate.notNull(bot);
        Validate.notNull(status);

        this.bot = bot;
        this.status = status;

        this.targetPhasename = "";
        this.targetProjectname = targetProjectname;
        this.targetOwnername = targetOwnername;
    }

    public StatisticTask(
            final User bot,
            final Status status,
            final String targetPhasename,
            final String targetProjectname,
            final String targetOwnername) {

        Validate.notNull(bot);
        Validate.notNull(status);

        this.bot = bot;
        this.status = status;

        this.targetPhasename = targetPhasename;
        this.targetProjectname = targetProjectname;
        this.targetOwnername = targetOwnername;
    }

    public StatisticTask(
            final User bot,
            final Status status,
            final String targetPhasename,
            final String targetProjectname,
            final String targetOwnername,
            final String targetAnnotatorname) {

        Validate.notNull(bot);
        Validate.notNull(status);

        this.bot = bot;
        this.status = status;

        this.targetPhasename = targetPhasename;
        this.targetProjectname = targetProjectname;
        this.targetOwnername = targetOwnername;
        this.targetAnnotatorname = targetAnnotatorname;
    }

}
