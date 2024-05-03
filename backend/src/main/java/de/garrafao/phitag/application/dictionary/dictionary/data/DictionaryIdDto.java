package de.garrafao.phitag.application.dictionary.dictionary.data;

import de.garrafao.phitag.domain.dictionary.dictionary.DictionaryId;
import lombok.Getter;

@Getter
public class DictionaryIdDto {

    private final String dname;
    private final String uname;

    private DictionaryIdDto(final String dname, final String uname) {
        this.dname = dname;
        this.uname = uname;
    }

    public static DictionaryIdDto from(final DictionaryId dictionaryId) {
        return new DictionaryIdDto(dictionaryId.getDname(), dictionaryId.getUname());
    }

    
}
