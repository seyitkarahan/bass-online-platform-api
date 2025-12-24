package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.entity.Rating;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RatingRepository {

    private final JdbcTemplate jdbcTemplate;

    public RatingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean hasEnrollment(Long studentId, Long courseId) {
        String sql = """
            SELECT COUNT(*) FROM enrollments
            WHERE student_id = ? AND course_id = ? AND active = true
        """;

        return jdbcTemplate.queryForObject(
                sql, Integer.class, studentId, courseId
        ) > 0;
    }

    public boolean alreadyRated(Long studentId, Long courseId) {
        String sql = """
            SELECT COUNT(*) FROM ratings
            WHERE student_id = ? AND course_id = ?
        """;

        return jdbcTemplate.queryForObject(
                sql, Integer.class, studentId, courseId
        ) > 0;
    }

    public void save(Rating rating) {
        String sql = """
            INSERT INTO ratings (student_id, course_id, rating, comment)
            VALUES (?, ?, ?, ?)
        """;

        jdbcTemplate.update(
                sql,
                rating.getStudentId(),
                rating.getCourseId(),
                rating.getRating(),
                rating.getComment()
        );
    }

    public Double averageRating(Long courseId) {
        String sql = """
            SELECT AVG(rating)
            FROM ratings
            WHERE course_id = ?
        """;

        return jdbcTemplate.queryForObject(sql, Double.class, courseId);
    }
}
