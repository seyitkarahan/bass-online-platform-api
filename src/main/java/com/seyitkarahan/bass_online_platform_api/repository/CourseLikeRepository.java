package com.seyitkarahan.bass_online_platform_api.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CourseLikeRepository {

    private final JdbcTemplate jdbcTemplate;

    public CourseLikeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean exists(Long userId, Long courseId) {
        String sql = """
            SELECT EXISTS (
                SELECT 1 FROM course_likes
                WHERE user_id = ? AND course_id = ?
            )
        """;

        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(sql, Boolean.class, userId, courseId)
        );
    }

    public void save(Long userId, Long courseId) {
        String sql = """
            INSERT INTO course_likes (user_id, course_id)
            VALUES (?, ?)
        """;
        jdbcTemplate.update(sql, userId, courseId);
    }
}
