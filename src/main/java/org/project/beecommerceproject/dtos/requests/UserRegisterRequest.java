package org.project.beecommerceproject.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserRegisterRequest {

    private String firstName;
    private String lastName;

    @NotNull(message = "email must be not null")
    @NotBlank(message = "email must be not blank")
    private String email;

    @NotNull(message = "password must be not null")
    @NotBlank(message = "password must be not blank")
    private String password;

    private String roleName;
}
