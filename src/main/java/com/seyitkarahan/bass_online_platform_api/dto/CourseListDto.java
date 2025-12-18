package com.seyitkarahan.bass_online_platform_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseListDto {

    private Long id;
    private String title;
    private double price;
    private String instructorName;
    private String categoryName;
}
