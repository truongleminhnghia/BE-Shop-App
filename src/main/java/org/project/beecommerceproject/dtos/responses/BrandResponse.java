package org.project.beecommerceproject.dtos.responses;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandResponse {

    private String id;
    private String brandName;
    private String description;
    private boolean status;
}
