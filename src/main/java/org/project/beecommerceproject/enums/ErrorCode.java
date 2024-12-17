package org.project.beecommerceproject.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    ROLE_MUST_BE_ENUM(HttpStatus.BAD_REQUEST, "Role must be enum"),
    USER_EXIST(HttpStatus.BAD_REQUEST, "User already exists");

    private HttpStatus code;
    private String message;
}
