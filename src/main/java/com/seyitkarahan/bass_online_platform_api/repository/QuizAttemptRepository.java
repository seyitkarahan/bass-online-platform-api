package com.seyitkarahan.bass_online_platform_api.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class QuizAttemptRepository {

    private final JdbcTemplate jdbcTemplate;

    public QuizAttemptRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long createAttempt(
            Long studentId,
            Long quizId,
            int totalQuestions
    ) {
        String sql = """
            INSERT INTO student_quiz_attempts
            (student_id, quiz_id, total_questions, completed)
            VALUES (?, ?, ?, false)
        """;

        jdbcTemplate.update(sql, studentId, quizId, totalQuestions);

        return jdbcTemplate.queryForObject(
                "SELECT LASTVAL()", Long.class
        );
    }

    public void completeAttempt(
            Long attemptId,
            int correctAnswers,
            int score
    ) {
        String sql = """
            UPDATE student_quiz_attempts
            SET correct_answers = ?, score = ?, completed = true
            WHERE id = ?
        """;

        jdbcTemplate.update(sql, correctAnswers, score, attemptId);
    }
}
