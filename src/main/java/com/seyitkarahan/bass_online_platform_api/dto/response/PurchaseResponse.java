package com.seyitkarahan.bass_online_platform_api.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseResponse {

    private Long enrollmentId;
    private Long courseId;
    private BigDecimal amount;
    private String paymentStatus;
}

