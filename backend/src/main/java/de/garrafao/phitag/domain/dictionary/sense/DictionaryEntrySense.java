package de.garrafao.phitag.domain.dictionary.sense;

import java.util.List;

import javax.persistence.*;

import org.apache.commons.lang3.Validate;

import de.garrafao.phitag.domain.dictionary.entry.DictionaryEntry;
import de.garrafao.phitag.domain.dictionary.example.DictionaryEntrySenseExample;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "phitagdictionaryentrysense")
@Getter
@ToString
public class DictionaryEntrySense {

    @EmbeddedId
    private DictionaryEntrySenseId id;

    @MapsId("dictionaryentryid")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "entryid", referencedColumnName = "entryid"),
            @JoinColumn(name = "dname", referencedColumnName = "dname"),
            @JoinColumn(name = "uname", referencedColumnName = "uname")
    })
    private DictionaryEntry dictionaryentry;

    @Setter
    @Column(name = "definition", nullable = false)
    private String definition;

    @Setter
    @Column(name = "senseorder", nullable = false)
    private int senseorder;

    @OneToMany(mappedBy = "dictionaryentrysense")
    private List<DictionaryEntrySenseExample> examples;

    DictionaryEntrySense() {
    }

    public DictionaryEntrySense(final DictionaryEntry dictionaryentry, final String definition, final int order) {
        Validate.notNull(dictionaryentry, "dictionaryentry must not be null");
        Validate.notEmpty(definition, "definition must not be null");
        Validate.isTrue(order >= 0, "order must be greater than or equal to 0");

        this.id = new DictionaryEntrySenseId(dictionaryentry.getId());
        this.dictionaryentry = dictionaryentry;

        this.definition = definition;
        this.senseorder = order;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof DictionaryEntrySense)) {
            return false;
        }

        DictionaryEntrySense other = (DictionaryEntrySense) obj;
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

}
