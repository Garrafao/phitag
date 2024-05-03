package de.garrafao.phitag.application.role;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.domain.role.Role;
import de.garrafao.phitag.domain.role.RoleRepository;

@Service
public class RoleApplicationService {
    
    private final RoleRepository roleRepository;

    @Autowired
    public RoleApplicationService(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Set<Role> getRoles() {
        return this.roleRepository.findAll();
    }

}
