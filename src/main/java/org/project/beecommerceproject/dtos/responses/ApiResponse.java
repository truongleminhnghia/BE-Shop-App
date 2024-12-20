package org.project.beecommerceproject.dtos.responses;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ApiResponse {

    private int code;
    private boolean success;
    private String message;
    private Object data;
}
