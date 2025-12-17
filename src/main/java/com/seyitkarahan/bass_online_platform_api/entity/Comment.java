package com.seyitkarahan.bass_online_platform_api.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    private Long id;
    private Long userId;
    private Long courseId;

    private String content;
    private LocalDateTime createdAt;
}
