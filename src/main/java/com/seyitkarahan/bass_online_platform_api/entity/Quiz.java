package com.seyitkarahan.bass_online_platform_api.entity;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quiz {

    private Long id;
    private Long courseId;
    private String title;
}
