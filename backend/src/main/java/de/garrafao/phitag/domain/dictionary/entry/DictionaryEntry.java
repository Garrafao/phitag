package de.garrafao.phitag.domain.dictionary.entry;

import java.util.List;

import javax.persistence.*;

import org.apache.commons.lang3.Validate;

import de.garrafao.phitag.domain.dictionary.dictionary.Dictionary;
import de.garrafao.phitag.domain.dictionary.sense.DictionaryEntrySense;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "phitagdictionaryentry")
@Getter
@ToString
public class DictionaryEntry {

    @EmbeddedId
    private DictionaryEntryId id;

    @MapsId("dictionaryid")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "dname", referencedColumnName = "dname"),
            @JoinColumn(name = "uname", referencedColumnName = "uname")
    })
    private Dictionary dictionary;

    @Setter
    @Column(name = "headword", nullable = false)
    private String headword;

    @Setter
    @Column(name = "partofspeech")
    private String partofspeech;

    @OneToMany(mappedBy = "dictionaryentry")
    private List<DictionaryEntrySense> senses;

    DictionaryEntry() {
    }

    public DictionaryEntry(final Dictionary dictionary, final String headword, final String partOfSpeech) {
        Validate.notNull(dictionary, "dictionary must not be null");
        Validate.notEmpty(headword, "headword must not be null");

        this.id = new DictionaryEntryId(dictionary.getId());
        this.dictionary = dictionary;

        this.headword = headword;
        this.partofspeech = partOfSpeech;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof DictionaryEntry)) {
            return false;
        }

        DictionaryEntry other = (DictionaryEntry) obj;
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

}
