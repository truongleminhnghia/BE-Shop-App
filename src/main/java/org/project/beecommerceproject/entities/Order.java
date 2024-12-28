package org.project.beecommerceproject.entities;


import jakarta.persistence.*;
import lombok.*;
import org.project.beecommerceproject.enums.EnumStatusOrder;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @Column(name = "total_quantity", nullable = false)
    private int totalQuantity;

    @Column(name = "order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumStatusOrder orderStatus;

    @Column(name = "full_name_Shipping", columnDefinition = "NVARCHAR(300)")
    private String fullNameShipping;

    @Column(name = "address_Shipping", columnDefinition = "NVARCHAR(300)", nullable = false)
    private String addressShipping;

    @Column(name = "phone_shipping", nullable = false)
    private String phoneShipping;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;
}
