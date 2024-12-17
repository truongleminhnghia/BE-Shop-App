package org.project.beecommerceproject.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.N;
import org.project.beecommerceproject.enums.EnumRoleName;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {

    private String id;
    private EnumRoleName roleName;
}
