package de.garrafao.phitag.infrastructure.rest;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.visibility.VisibilityApplicationService;
import de.garrafao.phitag.application.visibility.data.VisibilityDto;

@RestController
@RequestMapping(value = "/api/v1/visibility")
public class VisibilityResource {
    
    private final VisibilityApplicationService visibilityApplicationService;

    @Autowired
    public VisibilityResource(VisibilityApplicationService visibilityApplicationService) {
        this.visibilityApplicationService = visibilityApplicationService;
    }

    @RequestMapping(value = "/all")
    public Set<VisibilityDto> all() {
        return this.visibilityApplicationService.getVisibilityDtos();
    }
}
