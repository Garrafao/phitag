package de.garrafao.phitag.domain.dictionary.dictionary;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Embeddable
@EqualsAndHashCode
@ToString
public class DictionaryId implements Serializable {

    @Column(name = "dname")
    private String dname;

    private String uname;

    public DictionaryId() {
    }

    public DictionaryId(final String dname, final String uname) {
        this.dname = dname;
        this.uname = uname;
    }

}