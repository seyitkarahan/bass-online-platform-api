package com.seyitkarahan.bass_online_platform_api.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson {

    private Long id;
    private Long courseId;

    private String title;
    private String content;
}
