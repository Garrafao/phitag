package de.garrafao.phitag.domain.dictionary.entry;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import de.garrafao.phitag.domain.dictionary.dictionary.DictionaryId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Embeddable
@EqualsAndHashCode
@ToString
public class DictionaryEntryId implements Serializable {

    @Column(name = "entryid")
    private String entryid;

    private DictionaryId dictionaryid;

    public DictionaryEntryId() {
    }

    public DictionaryEntryId(final DictionaryId dictionaryid) {
        this.entryid = UUID.randomUUID().toString();
        this.dictionaryid = dictionaryid;
    }

    public DictionaryEntryId(final String entryid, final DictionaryId dictionaryid) {
        this.entryid = entryid;
        this.dictionaryid = dictionaryid;
    }

}
