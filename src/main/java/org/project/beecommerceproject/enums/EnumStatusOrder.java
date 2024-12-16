package org.project.beecommerceproject.enums;

import lombok.Getter;

@Getter
public enum EnumStatusOrder {
    PENDING,
    PENDING_PAYMENT,
    PAID,
    PROCESSING,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED,
    RETURNED,
    REFUNDED
}
