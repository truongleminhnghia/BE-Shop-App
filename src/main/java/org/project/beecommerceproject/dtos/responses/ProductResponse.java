package org.project.beecommerceproject.dtos.responses;

import lombok.*;
import org.project.beecommerceproject.entities.Brand;
import org.project.beecommerceproject.entities.Category;
import org.project.beecommerceproject.entities.Image;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private String productName;
    private double price;
    private String description;
    private Brand brand;
    private Category category;
    private boolean status;
    private Image image;
}
