package org.project.beecommerceproject.dtos.requests;

import lombok.*;
import org.project.beecommerceproject.entities.Brand;
import org.project.beecommerceproject.entities.Category;
import org.project.beecommerceproject.entities.Image;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreationRequest {

    private String productName;
    private double price;
    private String description;
    private Brand brand;
    private Category category;
    private float rate;
    private boolean status;
    private double priceDiscount;
    private Image image;
}
