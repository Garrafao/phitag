package de.garrafao.phitag.application.dictionary.example.data;

import de.garrafao.phitag.domain.dictionary.example.DictionaryEntrySenseExample;
import lombok.Getter;

@Getter
public class DictionaryEntrySenseExampleDto {

    private final DictionaryEntrySenseExampleIdDto id;

    private final String example;
    private Integer order;

    private DictionaryEntrySenseExampleDto(final DictionaryEntrySenseExampleIdDto id, final String example,
            final Integer order) {
        this.id = id;
        this.example = example;
        this.order = order;
    }

    public static DictionaryEntrySenseExampleDto from(final DictionaryEntrySenseExample example) {
        return new DictionaryEntrySenseExampleDto(
                DictionaryEntrySenseExampleIdDto.from(example.getId()),
                example.getExample(),
                example.getExampleorder());
    }

}
