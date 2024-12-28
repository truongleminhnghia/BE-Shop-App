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
    LIST_IS_EMPTY(HttpStatus.BAD_REQUEST, "List is empty"),
    USER_EXISTED(HttpStatus.BAD_REQUEST, "User existed"),
    ROLE_NOT_NULL(HttpStatus.BAD_REQUEST, "role name not null"),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "Token invalid"),
    NO_ACCESS(HttpStatus.FORBIDDEN, "No access"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
    USERNAME_OR_PASSWORD_INVALID(HttpStatus.UNAUTHORIZED, "Username or password invalid"),
    USER_DO_NOT_EXIST(HttpStatus.NOT_FOUND, "User don't exist"),
    USER_HAS_LOCKED(HttpStatus.CONFLICT, "User has locked"),
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "Token do not found"),
    EMAIL_NOT_NULL(HttpStatus.BAD_REQUEST, "Email must not be null"),
    TOKEN_DO_NOT_REFRESH(HttpStatus.OK, "Token do not refresh"),
    USER_ID_NOT_NULL(HttpStatus.BAD_REQUEST, "UserId must not be null"),
    USER_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "User update failed"),
    CATEGORY_EXISTS(HttpStatus.BAD_REQUEST, "Category already exists"),
    ID_IS_NOT_NULL(HttpStatus.BAD_REQUEST, "ID is not null"),
    NO_CURRENT_USER(HttpStatus.BAD_REQUEST, "User must be login"),
    CATEGORY_NAME_NOT_NULL(HttpStatus.BAD_REQUEST, "Category name not null"),
    CATEGORY_DO_NOT_EXIST(HttpStatus.NOT_FOUND, "Category do not exist"),
    PRODUCT_EXISTING(HttpStatus.BAD_REQUEST, "Product already exists"),
    BRAND_EXISTING(HttpStatus.BAD_REQUEST, "Brand already exists"),
    BRAND_DO_NOT_EXIST(HttpStatus.NOT_FOUND, "Brand don't exist"),
    USER_EXIST(HttpStatus.BAD_REQUEST, "User already exists");

    private HttpStatus code;
    private String message;
}
