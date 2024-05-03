package de.garrafao.phitag.infrastructure.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.status.StatusApplicationService;
import de.garrafao.phitag.application.status.data.StatusDto;

@RestController
@RequestMapping(value = "/api/v1/status")
public class StatusResource {

    private final StatusApplicationService statusApplicationService;

    @Autowired
    public StatusResource(StatusApplicationService statusApplicationService) {
        this.statusApplicationService = statusApplicationService;
    }

    @RequestMapping(value = "/all")
    public List<StatusDto> all() {
        return this.statusApplicationService.getStatusDtos();
    }
    
}
