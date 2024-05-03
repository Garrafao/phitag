package de.garrafao.phitag.application.role.data;

import de.garrafao.phitag.domain.role.Role;
import lombok.Getter;

@Getter
public class RoleDto {

    private final String name;
    private final String visiblename;

    private RoleDto(final String name, final String visiblename) {
        this.name = name;
        this.visiblename = visiblename;
    }

    public static RoleDto from(Role role) {
        return new RoleDto(role.getName(), role.getVisiblename());
    }
}