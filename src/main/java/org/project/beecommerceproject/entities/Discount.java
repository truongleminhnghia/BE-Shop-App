package org.project.beecommerceproject.entities;

import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.checker.units.qual.C;
import org.project.beecommerceproject.enums.EnumTypeDiscount;

@Entity
@Table(name = "discounts")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Discount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "discount_name", columnDefinition = "NVARCHAR(300)")
    private String discountName;

    @Column(name = "discount_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumTypeDiscount discountType;

    @Column(name = "discount_percentage", nullable = false)
    private float discountPercentage;

    @Column(name = "description", columnDefinition = "NVARCHAR(500)")
    private String description;

    @Column(name = "status_discount", nullable = false)
    private boolean statusDiscount;

}
