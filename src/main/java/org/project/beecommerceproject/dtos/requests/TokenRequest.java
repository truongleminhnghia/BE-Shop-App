package org.project.beecommerceproject.dtos.requests;

import lombok.*;
import org.project.beecommerceproject.enums.EnumTokenType;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenRequest {
    private String token;
    private EnumTokenType type;
}
