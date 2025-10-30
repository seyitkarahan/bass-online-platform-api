package com.seyitkarahan.bass_online_platform_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    private String paymentMethod; // örn: CREDIT_CARD, PAYPAL

    private String paymentStatus; // örn: SUCCESS, FAILED, PENDING

    private LocalDateTime paymentDate = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;
}
