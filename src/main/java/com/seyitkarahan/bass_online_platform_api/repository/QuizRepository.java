package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.dto.response.QuizResponse;
import com.seyitkarahan.bass_online_platform_api.entity.Quiz;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class QuizRepository {

    private final JdbcTemplate jdbcTemplate;

    public QuizRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Quiz quiz) {
        String sql = """
            INSERT INTO quizzes (course_id, title)
            VALUES (?, ?)
            RETURNING id
        """;

        return jdbcTemplate.queryForObject(
                sql,
                Long.class,
                quiz.getCourseId(),
                quiz.getTitle()
        );
    }

    public List<QuizResponse> findByCourse(Long courseId) {
        String sql = """
            SELECT id, title
            FROM quizzes
            WHERE course_id = ?
        """;

        return jdbcTemplate.query(sql,
                (rs, i) -> new QuizResponse(
                        rs.getLong("id"),
                        rs.getString("title")
                ),
                courseId);
    }
}
