package de.garrafao.phitag.infrastructure.rest;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.entitlement.EntitlementApplicationService;
import de.garrafao.phitag.application.entitlement.data.EntitlementDto;

@RestController
@RequestMapping(value = "/api/v1/entitlement")
public class EntitlementResource {
    
    private final EntitlementApplicationService entitlementApplicationService;

    @Autowired
    public EntitlementResource(EntitlementApplicationService entitlementApplicationService) {
        this.entitlementApplicationService = entitlementApplicationService;
    }

    @RequestMapping()
    public Set<EntitlementDto> all() {
        return this.entitlementApplicationService.getEntitlementDtos();
    }
}
