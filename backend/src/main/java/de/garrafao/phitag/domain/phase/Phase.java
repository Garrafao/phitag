package de.garrafao.phitag.domain.phase;

import de.garrafao.phitag.domain.annotationtype.AnnotationType;
import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.phase.data.PhaseStatusEnum;
import de.garrafao.phitag.domain.project.Project;
import de.garrafao.phitag.domain.sampling.Sampling;
import de.garrafao.phitag.domain.statistic.statisticannotationmeasure.StatisticAnnotationMeasure;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Validate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "phitagphase")
@Getter
public class Phase {
    
    @EmbeddedId
    private PhaseId id;

    @MapsId("projectid")
    @ManyToOne
    @JoinColumns({ 
        @JoinColumn(name = "projectname", referencedColumnName = "name"),
        @JoinColumn(name = "ownername", referencedColumnName = "ownername") 
    })
    private Project project;

    @Column(name = "displayname", unique = true, nullable = false)
    private String displayname;

    @ManyToOne
    @JoinColumn(name = "phitagannotationtype_name")
    private AnnotationType annotationType;

    @ManyToOne
    @JoinColumn(name = "phitagsamplingstrategy_name")
    private Sampling sampling;

    @Column(name = "description")
    private String description;

    @Column(name = "task_head")
    private String taskhead;

    @Setter
    @Column(name = "code")
    private String code;


    @Column(name = "isTutorial", nullable = false)
    private boolean isTutorial;

    @Column(name = "status", nullable = false)
    private String status;

    @Setter
    @ManyToMany
    @JoinTable(name = "phitagphase_phitagtutorial", 
        joinColumns = {
            @JoinColumn(name = "phitagphase_phasename", referencedColumnName = "name"),
            @JoinColumn(name = "phitagphase_projectname", referencedColumnName = "projectname"),
            @JoinColumn(name = "phitagphase_ownername", referencedColumnName = "ownername")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "phitagtutorial_phasename", referencedColumnName = "name"),
            @JoinColumn(name = "phitagtutorial_projectname", referencedColumnName = "projectname"),
            @JoinColumn(name = "phitagtutorial_ownername", referencedColumnName = "ownername")
        }
    )
    private List<Phase> tutorialRequirements;

    @ManyToOne
    @JoinColumn(name = "phitagstatisticannotationmeasures_id")
    private StatisticAnnotationMeasure statisticAnnotationMeasure;

    @Column(name = "statisticannotationmeasurethreshold")
    private Double statisticAnnotationMeasureThreshold;
    

    @ManyToMany(mappedBy = "completedTutorials",cascade = CascadeType.REMOVE)
    private List<Annotator> annotators;

    @Column(name = "instancepersample")
    private Integer instancePerSample;

    Phase() {
    }

    public Phase(final String name, final Project project, final AnnotationType annotationType, final Sampling sampling,
                 final String description, final String taskhead, final  Integer instancePerSample) {
        Validate.notEmpty(name);        
        Validate.matchesPattern(name, "^[a-zA-Z0-9-]+$");

        Validate.notNull(project);

        this.id = new PhaseId(name, project.getId());
        this.project = project;

        this.displayname = name;
        this.annotationType = annotationType;
        this.sampling = sampling;
        
        this.description = description;
        this.taskhead = taskhead;
        this.instancePerSample = instancePerSample;
        this.isTutorial = false;

        this.status = PhaseStatusEnum.OPEN.name();
    }

    public Phase(final String name, final Project project, final AnnotationType annotationType, final Sampling sampling,
                 final String description, final String taskhead, final  Integer instancePerSample,  final boolean isTutorial) {
        this(name, project, annotationType, sampling, description, taskhead, instancePerSample);
        this.isTutorial = isTutorial;
    }

    public Phase(final String code){
        this.code = code;
    }

    public Phase(final String name, final Project project, final AnnotationType annotationType,
                 final Sampling sampling, final String description, final String taskhead,
                 final  Integer instancePerSample,
                 final boolean isTutorial, final StatisticAnnotationMeasure statisticAnnotationMeasure, final Double statisticAnnotationMeasureThreshold) {
        this(name, project, annotationType, sampling, description, taskhead, instancePerSample,  isTutorial);
        this.statisticAnnotationMeasure = statisticAnnotationMeasure;
        this.statisticAnnotationMeasureThreshold = statisticAnnotationMeasureThreshold;
    }

    public void setStatus(final PhaseStatusEnum status) {
        this.status = status.name();
    }

    @Override
    public String toString() {
        return String.format("Phase [id=%s, projectId=%s]", id, project.getId());
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object == this) {
            return true;
        }
        if (!(object instanceof Phase)) {
            return false;
        }
        Phase phase = (Phase) object;
        return this.id.equals(phase.getId());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }


}
