package org.project.beecommerceproject.dtos.requests;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {

    private String categoryName;
    private String description;
    private boolean status;
}
