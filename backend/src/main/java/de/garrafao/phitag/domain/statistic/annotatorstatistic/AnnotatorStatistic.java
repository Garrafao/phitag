package de.garrafao.phitag.domain.statistic.annotatorstatistic;

import java.util.Map;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "phitagannotatorstatistic")
@IdClass(AnnotatorStatisticId.class)
@Data
public class AnnotatorStatistic {

    @Id
    @Column(name = "annotatorname", nullable = false)
    private String annotatorname; 

    @Id
    @Column(name = "ownername", nullable = false)
    private String ownername;

    @Id
    @Column(name = "projectname", nullable = false)
    private String projectname;

    @Column(name = "krippendorffalpha", nullable = false)
    private Double krippendorffalpha;

    @ElementCollection
    @CollectionTable(name = "phitag_annotatorstatistic_interannotator_krippendorffalpha", joinColumns = {
            @JoinColumn(name = "annotatorname", referencedColumnName = "annotatorname"),
            @JoinColumn(name = "ownername", referencedColumnName = "ownername"),
            @JoinColumn(name = "projectname", referencedColumnName = "projectname"),
    })
    @MapKeyColumn(name = "interannotator_name")
    @Column(name = "krippendorffalpha")
    private Map<String, Double> interannotatorKrippendorffalpha;

    @Column(name = "annotations", nullable = false)
    private Integer annotations;

    @ElementCollection
    @CollectionTable(name = "phitag_annotatorstatistic_phase_annotation", joinColumns = {
            @JoinColumn(name = "annotatorname", referencedColumnName = "annotatorname"),
            @JoinColumn(name = "ownername", referencedColumnName = "ownername"),
            @JoinColumn(name = "projectname", referencedColumnName = "projectname"),
    })
    @MapKeyColumn(name = "phase")
    @Column(name = "annotations")
    private Map<String, Integer> phaseAnnotationCountMap;

    public AnnotatorStatistic() {
    }

    public AnnotatorStatistic(final String annotatorname, final String ownername, final String projectname) {
        this.annotatorname = annotatorname;
        this.ownername = ownername;
        this.projectname = projectname;

        // initialize all data/maps
        krippendorffalpha = 0.0;
        interannotatorKrippendorffalpha = null;
        annotations = 0;
        phaseAnnotationCountMap = null;
    }

   
    

}
