package org.project.beecommerceproject.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_stock")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductStock {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private Size size;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    private int quantity;

}
