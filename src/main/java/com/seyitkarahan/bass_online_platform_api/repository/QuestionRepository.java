package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.dto.response.QuestionResponse;
import com.seyitkarahan.bass_online_platform_api.entity.Question;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionRepository {

    private final JdbcTemplate jdbcTemplate;

    public QuestionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Long quizId, String questionText, String answer) {
        String sql = """
            INSERT INTO questions (quiz_id, question_text, answer)
            VALUES (?, ?, ?)
        """;

        jdbcTemplate.update(sql, quizId, questionText, answer);
    }

    public List<QuestionResponse> findByQuiz(Long quizId) {
        String sql = """
            SELECT id, question_text
            FROM questions
            WHERE quiz_id = ?
        """;

        return jdbcTemplate.query(
                sql,
                (rs, i) -> new QuestionResponse(
                        rs.getLong("id"),
                        rs.getString("question_text")
                ),
                quizId
        );
    }

    public int countByQuiz(Long quizId) {
        String sql = """
        SELECT COUNT(*)
        FROM questions
        WHERE quiz_id = ?
    """;

        return jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                quizId
        );
    }

    public String findCorrectAnswer(Long questionId) {
        String sql = """
        SELECT answer
        FROM questions
        WHERE id = ?
    """;

        return jdbcTemplate.queryForObject(
                sql,
                String.class,
                questionId
        );
    }

}
