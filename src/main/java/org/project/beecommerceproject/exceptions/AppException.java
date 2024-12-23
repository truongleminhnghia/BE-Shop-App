package org.project.beecommerceproject.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import org.project.beecommerceproject.dtos.responses.ApiResponse;
import org.project.beecommerceproject.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AppException extends RuntimeException implements AccessDeniedHandler {
    private ErrorCode errorCode;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        ApiResponse res = ApiResponse.builder()
                .code(ErrorCode.NO_ACCESS.getCode().value())
                .success(false)
                .message(ErrorCode.NO_ACCESS.getMessage())
                .build();
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(res));
    }
}

