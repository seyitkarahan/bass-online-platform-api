package com.seyitkarahan.bass_online_platform_api.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckoutResponse {

    private Long paymentId;
    private BigDecimal totalAmount;
    private String paymentStatus;
}
