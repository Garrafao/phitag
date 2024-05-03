package de.garrafao.phitag.application.language.data;

import de.garrafao.phitag.domain.language.Language;
import lombok.Getter;

@Getter
public class LanguageDto {

    private final String name;

    private LanguageDto(String name) {
        this.name = name;
    }

    public static LanguageDto from(Language language) {
        return new LanguageDto(language.getName());
    }

}