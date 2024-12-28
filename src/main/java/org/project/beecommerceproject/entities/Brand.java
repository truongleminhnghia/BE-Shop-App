package org.project.beecommerceproject.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "brands")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Brand extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "brand_name", nullable = false, columnDefinition = "NVARCHAR(300)")
    private String brandName;

    @Column(name = "description", columnDefinition = "NVARCHAR(300)")
    private String description;

    @Column(name = "status")
    private boolean status;
}
