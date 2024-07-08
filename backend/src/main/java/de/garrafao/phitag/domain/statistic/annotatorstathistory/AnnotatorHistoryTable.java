package de.garrafao.phitag.domain.statistic.annotatorstathistory;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "phitagannotatorstatistichistory")
@Data
@Getter
@Setter
public class AnnotatorHistoryTable {

    @Id
    @Column(name = "timestamp")
    private String uuid;


    @Column(name = "annotatorname", nullable = false)
    private String annotatorname;

    @Column(name = "goldannotatorname", nullable = false)
    private String goldannotator;

    @Column(name = "ownername", nullable = false)
    private String ownername;

    @Column(name = "projectname", nullable = false)
    private String projectname;

    @Column(name = "phasename", nullable = false)
    private String phasename;

    @Column(name = "annotationmeasure", nullable = false)
    private String annotationMeasure;

    @Column(name = "agreement", nullable = false)
    private Double agreement;




   public   AnnotatorHistoryTable() {
    }

    public AnnotatorHistoryTable(String uuid, String annotatorname, String goldannotator, String ownername, String projectname, String phasename, String annotationMeasure, Double agreement) {
        this.uuid = uuid;
        this.annotatorname = annotatorname;
        this.goldannotator = goldannotator;
        this.ownername = ownername;
        this.projectname = projectname;
        this.phasename = phasename;
        this.annotationMeasure = annotationMeasure;
        this.agreement = agreement;
    }
}
