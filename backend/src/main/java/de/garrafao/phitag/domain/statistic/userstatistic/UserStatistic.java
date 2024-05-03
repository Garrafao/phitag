package de.garrafao.phitag.domain.statistic.userstatistic;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "phitaguserstatistic")
@Data
public class UserStatistic {

    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "projectcount", nullable = false)
    private Integer projectcount;

    @ElementCollection
    @CollectionTable(name = "phitag_projectlanguage_count", joinColumns = {
            @JoinColumn(name = "username", referencedColumnName = "username")
    })
    @MapKeyColumn(name = "language")
    @Column(name = "count")
    private Map<String, Integer> languageCountMap;

    @ElementCollection
    @CollectionTable(name = "phitag_annotationtype_count", joinColumns = {
            @JoinColumn(name = "username", referencedColumnName = "username")
    })
    @MapKeyColumn(name = "annotationtype")
    @Column(name = "count")
    private Map<String, Integer> annotationTypeCountMap;

    @ElementCollection
    @CollectionTable(name = "phitag_project_annotationcount", joinColumns = {
            @JoinColumn(name = "username", referencedColumnName = "username")
    })
    @MapKeyColumn(name = "project")
    @Column(name = "annotationcount")
    private Map<String, Integer> pojectAnnotationCountMap;

    public UserStatistic() {
    }

    public UserStatistic(String username) {
        this.username = username;

        // initialize all maps
        projectcount = 0;
        languageCountMap = new HashMap<>();
        annotationTypeCountMap = new HashMap<>();
        pojectAnnotationCountMap = new HashMap<>();

    }

}
