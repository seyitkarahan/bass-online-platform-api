package com.seyitkarahan.bass_online_platform_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CourseCommentDto {
    private String userName;
    private String content;
    private LocalDateTime createdAt;
}

