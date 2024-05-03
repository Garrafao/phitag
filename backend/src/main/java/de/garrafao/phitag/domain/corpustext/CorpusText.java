package de.garrafao.phitag.domain.corpustext;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import de.garrafao.phitag.domain.corpusinformation.CorpusInformation;
import lombok.Getter;

@Entity
@Table(name = "phitagcorpustext")
@Getter
public class CorpusText {

    @Id
    private String id;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "orthography", nullable = false)
    private String orthography;

    @JoinColumn(name = "previousid")
    private String previousid;

    @JoinColumn(name = "nextid")
    private String nextid;

    @ManyToOne
    @JoinColumn(name = "phitagcorpusinformation_id")
    private CorpusInformation corpusInformation;

    public CorpusText() {
    }

    public CorpusText(final String id, final String text, final String orthography, final String previousid, final String nextid,
            final CorpusInformation corpusInformation) {

        Validate.notBlank(id, "id must not be blank");
        Validate.notBlank(text, "text must not be blank");
        Validate.notBlank(orthography, "orthography must not be blank");
        Validate.notNull(corpusInformation, "corpusInformation must not be null");

        this.id = id;
        this.text = text;
        this.orthography = orthography;
        this.previousid = previousid;
        this.nextid = nextid;
        this.corpusInformation = corpusInformation;
    }

    @Override
    public String toString() {
        return "CorpusText [id=" + id + ", text=" + text + ", orthography=" + orthography + ", previous=" + previousid
                + ", next=" + nextid + ", corpusInformation=" + corpusInformation + "]";
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        CorpusText other = (CorpusText) obj;
        return id.equals(other.id);
    }

}
