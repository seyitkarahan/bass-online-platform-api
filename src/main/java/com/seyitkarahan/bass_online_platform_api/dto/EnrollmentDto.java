package com.seyitkarahan.bass_online_platform_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EnrollmentDto {

    private Long enrollmentId;
    private String courseTitle;
    private boolean active;
    private String paymentStatus;
}
