package de.garrafao.phitag.application.dictionary.entry.data;

import java.util.List;

import de.garrafao.phitag.application.dictionary.sense.data.DictionaryEntrySenseDto;
import de.garrafao.phitag.domain.dictionary.entry.DictionaryEntry;
import lombok.Getter;

@Getter
public class DictionaryEntryDto {

    private final DictionaryEntryIdDto id;

    private final String headword;
    private final String partofspeech;

    private final List<DictionaryEntrySenseDto> senses;

    private DictionaryEntryDto(
            final DictionaryEntryIdDto id, 
            final String headword, final String partofspeech,
            final List<DictionaryEntrySenseDto> senses) {
        this.id = id;
        this.headword = headword;
        this.partofspeech = partofspeech;

        // senses should be sorted by sense number, TODO: hacky?
        List<DictionaryEntrySenseDto> sortedSenses = senses.stream()
                .sorted((s1, s2) -> s1.getOrder().compareTo(s2.getOrder()))
                .toList();
        this.senses = sortedSenses;
    }

    public static DictionaryEntryDto from(final DictionaryEntry entry) {
        return new DictionaryEntryDto(
                DictionaryEntryIdDto.from(entry.getId()),
                entry.getHeadword(),
                entry.getPartofspeech(),
                entry.getSenses().stream().map(DictionaryEntrySenseDto::from).toList());
    }




    

}
