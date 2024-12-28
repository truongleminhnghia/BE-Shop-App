package org.project.beecommerceproject.entities;

import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.checker.units.qual.C;

@Entity
@Table(name = "feedbacks")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Feedback extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "comment", columnDefinition = "NVARCHAR(500)")
    private String comment;

    @Column(name = "rate")
    private float rate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id)")
    private Product product;

    @Column(name = "status", nullable = false)
    private boolean status;
}
