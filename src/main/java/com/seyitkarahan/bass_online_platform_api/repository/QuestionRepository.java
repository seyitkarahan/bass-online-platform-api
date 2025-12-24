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

    public void save(Question question) {
        String sql = """
            INSERT INTO questions (quiz_id, question_text, answer)
            VALUES (?, ?, ?)
        """;

        jdbcTemplate.update(sql,
                question.getQuizId(),
                question.getQuestionText(),
                question.getAnswer());
    }

    public List<QuestionResponse> findByQuiz(Long quizId) {
        String sql = """
            SELECT id, question_text
            FROM questions
            WHERE quiz_id = ?
        """;

        return jdbcTemplate.query(sql,
                (rs, i) -> new QuestionResponse(
                        rs.getLong("id"),
                        rs.getString("question_text")
                ),
                quizId);
    }
}
