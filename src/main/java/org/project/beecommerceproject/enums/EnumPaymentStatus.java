package org.project.beecommerceproject.enums;

import lombok.Getter;

@Getter
public enum EnumPaymentStatus {
    PENDING_PROCESS,
    PAYMENT_SUCCESSFUL,
    PAYMENT_FAILED,
    PAYMENT_CANCELED,
    PAYMENT_REFUND
}
