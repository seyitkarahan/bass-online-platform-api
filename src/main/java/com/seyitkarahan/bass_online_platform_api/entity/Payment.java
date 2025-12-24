package com.seyitkarahan.bass_online_platform_api.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    private Long id;
    private Long studentId;

    private BigDecimal amount;
    private String paymentMethod;
    private String paymentStatus; // PENDING, SUCCESS, FAILED

    private LocalDateTime paymentDate;
}
