package org.project.beecommerceproject.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, name = "product_name", columnDefinition = "NVARCHAR(300)")
    private String productName;

    @Column(name = "price")
    private double price;

    @Column(name = "description", columnDefinition = "NVARCHAR(500)")
    private String description;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "rate")
    private float rate;

    @Column(name = "status", nullable = false)
    private boolean status;

    @Column(name = "price_discount")
    private double priceDiscount;

    @JoinColumn(name = "image_id")
    @ManyToOne
    private Image image;

}
