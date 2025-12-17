package com.seyitkarahan.bass_online_platform_api.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    private Long id;
    private Long instructorId;
    private Long categoryId;

    private String title;
    private String description;

    private BigDecimal price;

    private LocalDateTime createdAt;
}
