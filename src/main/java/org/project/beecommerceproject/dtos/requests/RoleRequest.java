package org.project.beecommerceproject.dtos.requests;

import lombok.*;
import org.project.beecommerceproject.enums.EnumRoleName;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RoleRequest {
    private EnumRoleName roleName;
}
