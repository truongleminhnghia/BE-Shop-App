package org.project.beecommerceproject.entities;

import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.checker.units.qual.C;

@Entity
@Table(name = "categories")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "category_name", columnDefinition = "NVARCHAR(300)", nullable = false)
    private String categoryName;

    @Column(name = "description", columnDefinition = "NVARCHAR(300)")
    private String description;

    @Column(name = "status", nullable = false)
    private boolean status = true;
}
