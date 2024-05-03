package de.garrafao.phitag.domain.dictionary.example;

import javax.persistence.*;

import org.apache.commons.lang3.Validate;

import de.garrafao.phitag.domain.dictionary.sense.DictionaryEntrySense;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "phitagdictionaryentrysenseexample")
@Getter
@ToString
public class DictionaryEntrySenseExample {

    @EmbeddedId
    private DictionaryEntrySenseExampleId id;

    @MapsId("dictionaryentrysenseid")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "senseid", referencedColumnName = "senseid"),
            @JoinColumn(name = "entryid", referencedColumnName = "entryid"),
            @JoinColumn(name = "dname", referencedColumnName = "dname"),
            @JoinColumn(name = "uname", referencedColumnName = "uname")
    })
    private DictionaryEntrySense dictionaryentrysense;

    @Setter
    @Column(name = "example", nullable = false)
    private String example;

    @Setter
    @Column(name = "exampleorder", nullable = false)
    private Integer exampleorder;

    DictionaryEntrySenseExample() {
    }

    public DictionaryEntrySenseExample(final DictionaryEntrySense dictionaryentrysense, final String example,
            final Integer order) {
        Validate.notNull(dictionaryentrysense, "dictionaryentrysense must not be null");
        Validate.notEmpty(example, "example must not be null");
        Validate.isTrue(order >= 0, "order must be greater than or equal to 0");

        this.id = new DictionaryEntrySenseExampleId(dictionaryentrysense.getId());
        this.dictionaryentrysense = dictionaryentrysense;

        this.example = example;
        this.exampleorder = order;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof DictionaryEntrySenseExample)) {
            return false;
        }

        DictionaryEntrySenseExample other = (DictionaryEntrySenseExample) obj;
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

}
