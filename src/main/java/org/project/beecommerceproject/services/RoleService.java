package org.project.beecommerceproject.services;

import org.project.beecommerceproject.entities.Role;
import org.project.beecommerceproject.enums.EnumRoleName;

public interface RoleService {
    Role getRoleByName(EnumRoleName roleName);
}
