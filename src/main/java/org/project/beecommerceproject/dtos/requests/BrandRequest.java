package org.project.beecommerceproject.dtos.requests;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandRequest {
    private String brandName;
    private String description;
    private boolean status;
}
