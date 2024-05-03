package de.garrafao.phitag.application.entitlement;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.application.entitlement.data.EntitlementDto;
import de.garrafao.phitag.domain.entitlement.EntitlementRepository;

@Service
public class EntitlementApplicationService {
 
    private final EntitlementRepository entitlementRepository;
 
    @Autowired
    public EntitlementApplicationService(final EntitlementRepository entitlementRepository) {
        this.entitlementRepository = entitlementRepository;
    }
 
    public Set<EntitlementDto> getEntitlementDtos() {
        return this.entitlementRepository.findAll().stream().map(EntitlementDto::from).collect(Collectors.toSet());
    }
}
