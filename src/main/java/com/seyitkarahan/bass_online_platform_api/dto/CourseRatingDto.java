package com.seyitkarahan.bass_online_platform_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseRatingDto {
    private int rating;
    private String comment;
    private String studentName;
}
