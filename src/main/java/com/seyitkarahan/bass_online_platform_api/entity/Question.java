package com.seyitkarahan.bass_online_platform_api.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {

    private Long id;
    private Long quizId;

    private String questionText;
    private String answer;
}

