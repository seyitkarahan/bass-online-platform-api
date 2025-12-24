package com.seyitkarahan.bass_online_platform_api.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentQuizAttempt {

    private Long id;
    private Long studentId;
    private Long quizId;

    private int totalQuestions;
    private int correctAnswers;
    private int score;

    private boolean completed;
}
