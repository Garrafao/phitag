package de.garrafao.phitag.application.dictionary.sense.data;

import java.util.List;

import de.garrafao.phitag.application.dictionary.example.data.DictionaryEntrySenseExampleDto;
import de.garrafao.phitag.domain.dictionary.sense.DictionaryEntrySense;
import lombok.Getter;

@Getter
public class DictionaryEntrySenseDto {

    private final DictionaryEntrySenseIdDto id;

    private final String definition;
    private final Integer order;

    private final List<DictionaryEntrySenseExampleDto> examples;

    private DictionaryEntrySenseDto(
            final DictionaryEntrySenseIdDto id, 
            final String definition, final Integer order,
            final List<DictionaryEntrySenseExampleDto> examples) {
        this.id = id;
        this.definition = definition;
        this.order = order;

        // examples should be sorted by example number, TODO: hacky?
        List<DictionaryEntrySenseExampleDto> sortedExamples = examples.stream()
                .sorted((e1, e2) -> e1.getOrder().compareTo(e2.getOrder()))
                .toList();
        this.examples = sortedExamples;

    }

    public static DictionaryEntrySenseDto from(final DictionaryEntrySense sense) {
        return new DictionaryEntrySenseDto(
                DictionaryEntrySenseIdDto.from(sense.getId()),
                sense.getDefinition(),
                sense.getSenseorder(),
                sense.getExamples().stream().map(DictionaryEntrySenseExampleDto::from).toList());
    }

}
