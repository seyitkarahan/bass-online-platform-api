package com.seyitkarahan.bass_online_platform_api.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseCreateRequest {
    private Long categoryId;
    private String title;
    private String description;
    private BigDecimal price;
}
