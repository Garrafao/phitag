package de.garrafao.phitag.application.dictionary.entry.data;

import de.garrafao.phitag.domain.dictionary.entry.DictionaryEntryId;
import lombok.Getter;

@Getter
public class DictionaryEntryIdDto {

    private final String id;
    private final String dname;
    private final String uname;

    private DictionaryEntryIdDto(final String id, final String dname, final String uname) {
        this.id = id;
        this.dname = dname;
        this.uname = uname;
    }

    public static DictionaryEntryIdDto from(final DictionaryEntryId dictionaryEntryId) {
        return new DictionaryEntryIdDto(
                dictionaryEntryId.getEntryid(),
                dictionaryEntryId.getDictionaryid().getDname(),
                dictionaryEntryId.getDictionaryid().getUname());
    }

}
