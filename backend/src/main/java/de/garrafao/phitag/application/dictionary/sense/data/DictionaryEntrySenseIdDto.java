package de.garrafao.phitag.application.dictionary.sense.data;

import de.garrafao.phitag.domain.dictionary.sense.DictionaryEntrySenseId;
import lombok.Getter;

@Getter
public class DictionaryEntrySenseIdDto {

    private final String id;
    private final String entryId;
    private final String dname;
    private final String uname;

    private DictionaryEntrySenseIdDto(final String id, final String entryId, final String dname, final String uname) {
        this.id = id;
        this.entryId = entryId;
        this.dname = dname;
        this.uname = uname;
    }

    public static DictionaryEntrySenseIdDto from(final DictionaryEntrySenseId dictionaryEntrySenseId) {
        return new DictionaryEntrySenseIdDto(
                dictionaryEntrySenseId.getSenseid(),
                dictionaryEntrySenseId.getDictionaryentryid().getEntryid(),
                dictionaryEntrySenseId.getDictionaryentryid().getDictionaryid().getDname(),
                dictionaryEntrySenseId.getDictionaryentryid().getDictionaryid().getUname());
    }

}
