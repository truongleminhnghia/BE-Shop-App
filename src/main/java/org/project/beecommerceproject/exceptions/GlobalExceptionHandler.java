package org.project.beecommerceproject.exceptions;

import org.project.beecommerceproject.dtos.responses.ApiResponse;
import org.project.beecommerceproject.enums.ErrorCode;
import org.springframework.http.ResponseEntity;
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
}
