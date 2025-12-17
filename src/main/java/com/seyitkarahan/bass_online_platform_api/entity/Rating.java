package com.seyitkarahan.bass_online_platform_api.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {

    private Long id;
    private Long studentId;
    private Long courseId;

    private int rating;
    private String comment;

    private LocalDateTime createdAt;
}

