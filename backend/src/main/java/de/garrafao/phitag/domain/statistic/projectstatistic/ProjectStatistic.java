package de.garrafao.phitag.domain.statistic.projectstatistic;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "phitagprojectstatistic")
@IdClass(ProjectStatisticId.class)
@Data
public class ProjectStatistic {

    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Id
    @Column(name = "projectname", nullable = false)
    private String projectname;

    @Column(name = "lemmacount", nullable = false)
    private Integer lemmacount;

    @Column(name = "usagecount", nullable = false)
    private Integer usagecount;


    @ElementCollection
    @CollectionTable(name = "phitag_usages_per_lemma", joinColumns = {
            @JoinColumn(name = "username", referencedColumnName = "username"),
            @JoinColumn(name = "projectname", referencedColumnName = "projectname")
    })
    @MapKeyColumn(name = "lemma")
    @Column(name = "count")
    private Map<String, Integer> usagesPerLemmaCountMap;

    public ProjectStatistic() {
    }

    public ProjectStatistic(String username, String projectname) {
        this.username = username;
        this.projectname = projectname;

        // initialize all data/maps
        lemmacount = 0;
        usagecount = 0;
        usagesPerLemmaCountMap = new HashMap<>();
    }

}
