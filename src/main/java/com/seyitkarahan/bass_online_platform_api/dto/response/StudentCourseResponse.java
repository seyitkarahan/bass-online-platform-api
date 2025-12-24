package com.seyitkarahan.bass_online_platform_api.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCourseResponse {

    private Long courseId;
    private String title;
    private String description;
    private BigDecimal price;
    private String categoryName;
    private LocalDateTime enrolledAt;
}
