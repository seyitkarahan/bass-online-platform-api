package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.dto.response.QuizResponse;
import com.seyitkarahan.bass_online_platform_api.entity.Quiz;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuizRepository {

    private final JdbcTemplate jdbcTemplate;

    public QuizRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Long courseId, String title) {
        String sql = """
            INSERT INTO quizzes (course_id, title)
            VALUES (?, ?)
            RETURNING id
        """;

        return jdbcTemplate.queryForObject(
                sql,
                Long.class,
                courseId,
                title
        );
    }

    public List<QuizResponse> findByCourse(Long courseId) {
        String sql = """
            SELECT id, title
            FROM quizzes
            WHERE course_id = ?
        """;

        return jdbcTemplate.query(
                sql,
                (rs, i) -> new QuizResponse(
                        rs.getLong("id"),
                        rs.getString("title")
                ),
                courseId
        );
    }

    public boolean existsById(Long quizId) {
        String sql = "SELECT COUNT(*) FROM quizzes WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, quizId);
        return count != null && count > 0;
    }
}
