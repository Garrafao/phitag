package de.garrafao.phitag.application.dictionary.dictionary.data;

import de.garrafao.phitag.domain.dictionary.dictionary.Dictionary;
import lombok.Getter;

@Getter
public class DictionaryDto {

    private final DictionaryIdDto id;
    private final String description;

    private DictionaryDto(final DictionaryIdDto id, final String description) {
        this.id = id;
        this.description = description;
    }

    public static DictionaryDto from(final Dictionary dictionary) {
        return new DictionaryDto(
                DictionaryIdDto.from(dictionary.getId()),
                dictionary.getDescription());
    }

}
