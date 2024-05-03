package de.garrafao.phitag.infrastructure.rest;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.language.LanguageApplicationService;
import de.garrafao.phitag.application.language.data.LanguageDto;


@RestController
@RequestMapping(value = "/api/v1/language")
public class LanguageResource {
    
    private final LanguageApplicationService languageApplicationService;

    @Autowired
    public LanguageResource(LanguageApplicationService languageApplicationService) {
        this.languageApplicationService = languageApplicationService;
    }

    @GetMapping()
    public Set<LanguageDto> all() {
        return this.languageApplicationService.getLanguageDtos();
    }
    
}
