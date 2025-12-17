package com.seyitkarahan.bass_online_platform_api.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    private Long id;
    private Long userId;
    private String gradeLevel;
}

