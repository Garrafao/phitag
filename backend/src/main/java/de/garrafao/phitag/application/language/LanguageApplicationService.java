package de.garrafao.phitag.application.language;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.application.language.data.LanguageDto;
import de.garrafao.phitag.domain.language.LanguageRepository;

@Service
public class LanguageApplicationService {

    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageApplicationService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public Set<LanguageDto> getLanguageDtos() {
        return this.languageRepository.findAll().stream().map(LanguageDto::from).collect(Collectors.toSet());
    }

}
