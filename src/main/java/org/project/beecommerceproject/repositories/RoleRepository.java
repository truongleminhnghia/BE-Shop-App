package org.project.beecommerceproject.repositories;

import org.project.beecommerceproject.entities.Role;
import org.project.beecommerceproject.enums.EnumRoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Role findByRoleName(EnumRoleName roleName);
}
