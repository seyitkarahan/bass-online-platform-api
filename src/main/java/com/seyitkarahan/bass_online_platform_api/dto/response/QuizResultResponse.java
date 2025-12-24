package com.seyitkarahan.bass_online_platform_api.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizResultResponse {

    private int totalQuestions;
    private int correctAnswers;
    private int score;
}
