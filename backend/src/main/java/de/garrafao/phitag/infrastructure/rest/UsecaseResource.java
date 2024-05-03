package de.garrafao.phitag.infrastructure.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.usecase.UsecaseApplicationService;
import de.garrafao.phitag.application.usecase.data.UsecaseDto;

@RestController
@RequestMapping(value = "/api/v1/usecase")
public class UsecaseResource {

    private final UsecaseApplicationService usecaseApplicationService;

    @Autowired
    public UsecaseResource(UsecaseApplicationService usecaseApplicationService) {
        this.usecaseApplicationService = usecaseApplicationService;
    }

    @GetMapping(value = "/all")
    public List<UsecaseDto> all() {
        return this.usecaseApplicationService.getUsecaseDtos();
    }
    
}
