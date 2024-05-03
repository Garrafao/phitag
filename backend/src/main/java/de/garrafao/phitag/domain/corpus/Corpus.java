package de.garrafao.phitag.domain.corpus;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.garrafao.phitag.domain.corpuslexicon.CorpusLexicon;
import de.garrafao.phitag.domain.corpustext.CorpusText;
import lombok.Getter;

@Entity
@Table(name = "phitagcorpus")
@Getter
public class Corpus {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "phitagcorpustext_id")
    private CorpusText corpusText;

    @ManyToOne
    @JoinColumn(name = "phitagcorpuslexicon_id")
    private CorpusLexicon corpusLexicon;

    public Corpus() {
    }

    public Corpus(String id, CorpusText corpusText, CorpusLexicon corpusLexicon) {
        this.id = id;
        this.corpusText = corpusText;
        this.corpusLexicon = corpusLexicon;
    }

    @Override
    public String toString() {
        return "Corpus [id=" + id + ", corpusText=" + corpusText + ", corpusLexicon=" + corpusLexicon + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((corpusLexicon == null) ? 0 : corpusLexicon.hashCode());
        result = prime * result + ((corpusText == null) ? 0 : corpusText.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Corpus other = (Corpus) obj;
        return id.equals(other.id);
    }

}
