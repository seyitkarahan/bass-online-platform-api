package com.seyitkarahan.bass_online_platform_api.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StudentAnswerRepository {

    private final JdbcTemplate jdbcTemplate;

    public StudentAnswerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(
            Long attemptId,
            Long questionId,
            String answer,
            boolean correct
    ) {
        String sql = """
            INSERT INTO student_answers
            (attempt_id, question_id, answer, correct)
            VALUES (?, ?, ?, ?)
        """;

        jdbcTemplate.update(
                sql,
                attemptId,
                questionId,
                answer,
                correct
        );
    }
}
