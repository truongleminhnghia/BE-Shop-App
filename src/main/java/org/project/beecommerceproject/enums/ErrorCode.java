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
    USER_EXISTED(HttpStatus.BAD_REQUEST, "User existed"),
    ROLE_NOT_NULL(HttpStatus.BAD_REQUEST, "role name not null"),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "Token invalid"),
    NO_ACCESS(HttpStatus.FORBIDDEN, "No access"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
    USERNAME_OR_PASSWORD_INVALID(HttpStatus.UNAUTHORIZED, "Username or password invalid"),
    USER_DO_NOT_EXIST(HttpStatus.NOT_FOUND, "User don't exist"),
    USER_HAS_LOCKED(HttpStatus.CONFLICT, "User has locked"),
    USER_EXIST(HttpStatus.BAD_REQUEST, "User already exists");

    private HttpStatus code;
    private String message;
}
