package com.seyitkarahan.bass_online_platform_api.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponse {

    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
}
