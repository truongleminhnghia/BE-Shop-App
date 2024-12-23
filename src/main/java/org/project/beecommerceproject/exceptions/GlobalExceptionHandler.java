package org.project.beecommerceproject.exceptions;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.project.beecommerceproject.dtos.responses.ApiResponse;
import org.project.beecommerceproject.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity.status(errorCode.getCode())
                .body(new ApiResponse(errorCode.getCode().value(), false, errorCode.getMessage(), null));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse> handlingException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(ErrorCode.INTERNAL_SERVER_ERROR.getCode().value(), false, exception.getMessage(), null));
    }

    @ExceptionHandler(value =MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String enumKey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(enumKey);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(errorCode.getCode().value(),false ,errorCode.getMessage(), null));
    }

    @ExceptionHandler({org.springframework.security.authentication.BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<ApiResponse> handleAuthenticationException() {
        ErrorCode errorCode = ErrorCode.USERNAME_OR_PASSWORD_INVALID;
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(errorCode.getCode().value(), false,errorCode.getMessage(), null));
    }

    @ExceptionHandler({ExpiredJwtException.class, MalformedJwtException.class, JwtException.class})
    public ResponseEntity<ApiResponse> handleTokenValidationException(ExpiredJwtException e) {
        System.out.println("Handling expired JWT exception: " + e.getMessage());
        ErrorCode errorCode = ErrorCode.TOKEN_INVALID;
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(errorCode.getCode().value(),false ,errorCode.getMessage(), null));
    }

}
