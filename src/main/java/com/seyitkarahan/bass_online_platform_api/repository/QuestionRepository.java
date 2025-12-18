package com.seyitkarahan.bass_online_platform_api.repository;

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

    public List<Question> findByQuizId(Long quizId) {
        String sql = "SELECT * FROM questions WHERE quiz_id = ?";
        return jdbcTemplate.query(sql, (rs, i) ->
                Question.builder()
                        .id(rs.getLong("id"))
                        .quizId(rs.getLong("quiz_id"))
                        .questionText(rs.getString("question_text"))
                        .answer(rs.getString("answer"))
                        .build(), quizId
        );
    }

    public Long save(Question question) {
        String sql = """
            INSERT INTO questions (quiz_id, question_text, answer)
            VALUES (?, ?, ?)
            RETURNING id
        """;

        return jdbcTemplate.queryForObject(
                sql,
                Long.class,
                question.getQuizId(),
                question.getQuestionText(),
                question.getAnswer()
        );
    }
}
