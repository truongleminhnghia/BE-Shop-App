package org.project.beecommerceproject.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class LoginRequest {

    @NotNull(message = "email must be not null")
    @NotBlank(message = "email must be not blank")
    private String email;

    @NotNull(message = "password must be not null")
    @NotBlank(message = "password must be not blank")
    @Size(min = 8, message = "Password must be more than 8 characters")
    private String password;
}
