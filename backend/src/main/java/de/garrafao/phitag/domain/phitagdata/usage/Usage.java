package de.garrafao.phitag.domain.phitagdata.usage;

import de.garrafao.phitag.domain.helper.Pair;
import de.garrafao.phitag.domain.phitagdata.IPhitagDataType;
import de.garrafao.phitag.domain.project.Project;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Validate;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "phitagusage")
@Getter
public class Usage implements IPhitagDataType {

    @EmbeddedId
    private UsageId id;

    @MapsId("projectid")
    @ManyToOne
    @JoinColumns({ 
        @JoinColumn(name = "projectname", referencedColumnName = "name"),
        @JoinColumn(name = "ownername", referencedColumnName = "ownername") 
    })
    private Project project;

    @Setter
    @Column(name = "context", nullable = false)
    private String context;

    @Setter
    @Column(name = "indices_target_token")
    private String indexTargetToken;

    @Setter
    @Column(name = "indices_target_sentence")
    private String indexTargetSentence;

    @Setter
    @Column(name = "lemma")
    private String lemma;

    @Setter
    @Column(name = "phitaggroup", nullable = false)
    private String group;

    public Usage() {
    }

    public Usage(final Project project, final String dataId, final String context, final String indexTargetToken,
            final String indexTargetSentence, final String lemma) {
        Validate.notNull(project);
        Validate.notNull(dataId);
        Validate.notNull(context);
        Validate.notEmpty(context);
       // Validate.notNull(indexTargetSentence);
        //Validate.notEmpty(indexTargetToken);
        //Validate.notNull(lemma);
       // Validate.notEmpty(lemma);

        this.project = project;
        this.id = new UsageId(dataId, project.getId());

        this.context = context;
        this.indexTargetToken = indexTargetToken;
        this.indexTargetSentence = indexTargetSentence;
        this.lemma = lemma;

        // Default value
        this.group = "";
    }

    public Usage(final Project project, final String dataId, final String context, final String indexTargetToken,
    final String indexTargetSentence, final String lemma, final String group) {
        this(project, dataId, context, indexTargetToken, indexTargetSentence, lemma);

        Validate.notNull(group);
        this.group = group;
    }

    public List<Pair<Integer, Integer>> getIndicesTargetToken() {
        return Arrays.asList(this.indexTargetToken.split(",")).stream().map(index -> {
            String[] indexArray = index.split(":");
            return new Pair<>(Integer.parseInt(indexArray[0]), Integer.parseInt(indexArray[1]));
        }).collect(Collectors.toList());
    }

    public List<Pair<Integer, Integer>> getIndicesTargetSentence() {
        return Arrays.asList(this.indexTargetSentence.split(",")).stream().map(index -> {
            String[] indexArray = index.split(":");
            return new Pair<>(Integer.parseInt(indexArray[0]), Integer.parseInt(indexArray[1]));
        }).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.format("PhaseData [id=%s]", id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Usage other = (Usage) obj;
        return this.id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
