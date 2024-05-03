package de.garrafao.phitag.domain.annotationprocessinformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.phase.Phase;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "phitagannotationprocessinformation")
@Getter
public class AnnotationProcessInformation {

    @EmbeddedId
    private AnnotationProcessInformationId id;

    @MapsId("annotatorid")
    @ManyToOne
    private Annotator annotator;

    @MapsId("phaseid")
    @ManyToOne
    private Phase phase;

    @Setter
    @Column(name = "instanceorder")
    private String order;

    @Setter
    @Column(name = "instanceindex")
    private Integer index;

    AnnotationProcessInformation() {
    }

    public AnnotationProcessInformation(Annotator annotator, Phase phase) {
        this.id = new AnnotationProcessInformationId(annotator.getId(), phase.getId());
        this.annotator = annotator;
        this.phase = phase;
    }

    public List<String> getOrder() {
        return new ArrayList<>(Arrays.asList(order.split(",")));
    }

    public String next() {
        if (index == null || index >= getOrder().size())
            return null;
        return getOrder().get(index);
    }

}
