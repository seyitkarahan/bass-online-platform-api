package com.seyitkarahan.bass_online_platform_api.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyCourseResponse {

    private Long courseId;
    private String title;
    private String description;

    private LocalDateTime enrolledAt;
}
