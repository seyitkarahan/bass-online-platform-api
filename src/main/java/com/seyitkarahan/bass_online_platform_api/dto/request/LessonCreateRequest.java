package com.seyitkarahan.bass_online_platform_api.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonCreateRequest {
    private Long courseId;
    private String title;
    private String content;
}
