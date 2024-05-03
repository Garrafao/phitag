package de.garrafao.phitag.domain.corpuslexicon;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import de.garrafao.phitag.domain.corpuslexicon.projection.SparseCorpusLexiconProjectionId;
import de.garrafao.phitag.domain.corpuslexicon.projection.SparseCorpusLexiconProjectionLemma;
import de.garrafao.phitag.domain.corpuslexicon.projection.SparseCorpusLexiconProjectionPoS;
import lombok.Getter;

@Entity
@Table(name = "phitagcorpuslexicon")
@Getter
public class CorpusLexicon implements SparseCorpusLexiconProjectionId, SparseCorpusLexiconProjectionLemma, SparseCorpusLexiconProjectionPoS {
    
    @Id
    private String id;

    @Column(name = "lemma", nullable = false)
    private String lemma;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "pos", nullable = false)
    private String pos;

    
    public CorpusLexicon() {
    }

    public CorpusLexicon(String id, String lemma, String token, String pos) {
        Validate.notBlank(id, "id must not be blank");
        this.id = id;
        
        this.lemma = lemma;
        this.token = token;
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "CorpusLexicon [id=" + id + ", lemma=" + lemma + ", token=" + token + ", pos=" + pos + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lemma == null) ? 0 : lemma.hashCode());
        result = prime * result + ((pos == null) ? 0 : pos.hashCode());
        result = prime * result + ((token == null) ? 0 : token.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        CorpusLexicon other = (CorpusLexicon) obj;
        return id.equals(other.id);
    }

}
