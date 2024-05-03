package de.garrafao.phitag.application.dictionary.example.data;

import de.garrafao.phitag.domain.dictionary.example.DictionaryEntrySenseExampleId;
import lombok.Getter;

@Getter
public class DictionaryEntrySenseExampleIdDto {

    private final String id;
    private final String senseId;
    private final String entryId;
    private final String dname;
    private final String uname;

    private DictionaryEntrySenseExampleIdDto(final String id, final String senseId, final String entryId,
            final String dname, final String uname) {
        this.id = id;
        this.senseId = senseId;
        this.entryId = entryId;
        this.dname = dname;
        this.uname = uname;
    }

    public static DictionaryEntrySenseExampleIdDto from(final DictionaryEntrySenseExampleId id) {
        return new DictionaryEntrySenseExampleIdDto(
                id.getExampleid(),
                id.getDictionaryentrysenseid().getSenseid(),
                id.getDictionaryentrysenseid().getDictionaryentryid().getEntryid(),
                id.getDictionaryentrysenseid().getDictionaryentryid().getDictionaryid().getDname(),
                id.getDictionaryentrysenseid().getDictionaryentryid().getDictionaryid().getUname());
    }

}
