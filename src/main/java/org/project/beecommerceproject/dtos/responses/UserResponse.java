package org.project.beecommerceproject.dtos.responses;

import lombok.*;
import org.project.beecommerceproject.enums.EnumStatusUser;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String avatar;
    private String googleAccountId;
    private String facebookAccountId;
    private boolean lockUser;
    private EnumStatusUser status;
    private RoleResponse role;

}
