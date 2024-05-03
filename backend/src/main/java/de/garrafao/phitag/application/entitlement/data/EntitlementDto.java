package de.garrafao.phitag.application.entitlement.data;

import de.garrafao.phitag.domain.entitlement.Entitlement;
import lombok.Getter;

@Getter
public class EntitlementDto {

    private final String name;
    private final String visiblename;
    
    private EntitlementDto(final String name, final String visiblename) {
        this.name = name;
        this.visiblename = visiblename;
    }

    public static EntitlementDto from(final Entitlement entitlement) {
        return new EntitlementDto(entitlement.getName(), entitlement.getVisiblename());
    }

}
