package de.garrafao.phitag.domain.statistic.phasestatistic;

import java.util.Map;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "phitagphasestatistic")
@IdClass(PhaseStatisticId.class)
@Data
public class PhaseStatistic {

    @Id
    @Column(name = "ownername", nullable = false)
    private String ownername;

    @Id
    @Column(name = "projectname", nullable = false)
    private String projectname;

    @Id
    @Column(name = "phasename", nullable = false)
    private String phasename;

    @Column(name = "annotations", nullable = false)
    private Integer annotations;

    @ElementCollection
    @CollectionTable(name = "phitag_phasestatistic_annotator_annotations", joinColumns = {
        @JoinColumn(name = "ownername", referencedColumnName = "ownername"),
        @JoinColumn(name = "projectname", referencedColumnName = "projectname"),
        @JoinColumn(name = "phasename", referencedColumnName = "phasename"),
    })
    @MapKeyColumn(name = "annotator")
    @Column(name = "annotations")
    private Map<String, Integer> annotatorAnnotationCountMap;

    @Column(name = "krippendorffalpha", nullable = false)
    private Double krippendorffalpha;

    @ElementCollection
    @CollectionTable(name = "phitag_phasestatistic_interannotator_krippendorffalpha", joinColumns = {
        @JoinColumn(name = "ownername", referencedColumnName = "ownername"),
        @JoinColumn(name = "projectname", referencedColumnName = "projectname"),
        @JoinColumn(name = "phasename", referencedColumnName = "phasename"),
    })
    @MapKeyColumn(name = "interannotator_name")
    @Column(name = "krippendorffalpha")
    private Map<String, Double> interannotatorKrippendorffalpha;

    public PhaseStatistic() {
    }

    public PhaseStatistic(final String ownername, final String projectname, final String phasename) {
        this.ownername = ownername;
        this.projectname = projectname;
        this.phasename = phasename;

        // initialize all data/maps
        this.annotations = 0;
        this.annotatorAnnotationCountMap = null;
        this.krippendorffalpha = 0.0;
        this.interannotatorKrippendorffalpha = null;
    }

    
}
