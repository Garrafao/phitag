package de.garrafao.phitag.domain.dictionary.sense;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import de.garrafao.phitag.domain.dictionary.entry.DictionaryEntryId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Embeddable
@EqualsAndHashCode
@ToString
public class DictionaryEntrySenseId implements Serializable {

    @Column(name = "senseid")
    private String senseid;

    private DictionaryEntryId dictionaryentryid;

    public DictionaryEntrySenseId() {
    }

    public DictionaryEntrySenseId(final DictionaryEntryId dictionaryentryid) {
        this.senseid = UUID.randomUUID().toString();
        this.dictionaryentryid = dictionaryentryid;
    }

    public DictionaryEntrySenseId(final String senseid, final DictionaryEntryId dictionaryentryid) {
        this.senseid = senseid;
        this.dictionaryentryid = dictionaryentryid;
    }
    
}
