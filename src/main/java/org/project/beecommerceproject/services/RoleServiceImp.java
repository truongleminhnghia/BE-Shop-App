package org.project.beecommerceproject.services;

import org.project.beecommerceproject.dtos.requests.RoleRequest;
import org.project.beecommerceproject.entities.Role;
import org.project.beecommerceproject.enums.EnumRoleName;
import org.project.beecommerceproject.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImp implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getRoleByName(EnumRoleName roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public Role save(RoleRequest request) {
        Role role = new Role();
        role.setRoleName(request.getRoleName());
        return roleRepository.save(role);
    }
}
