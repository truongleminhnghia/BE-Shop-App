package org.project.beecommerceproject.exceptions;

import lombok.*;
import org.project.beecommerceproject.enums.ErrorCode;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AppException extends RuntimeException {
    private ErrorCode errorCode;
}
