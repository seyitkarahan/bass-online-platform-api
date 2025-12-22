package com.seyitkarahan.bass_online_platform_api.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseUpdateRequest {
    private String title;
    private String description;
    private BigDecimal price;
}
