package org.project.beecommerceproject.dtos.responses;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {

    private String id;
    private String categoryName;
    private String description;
    private boolean status;
}
