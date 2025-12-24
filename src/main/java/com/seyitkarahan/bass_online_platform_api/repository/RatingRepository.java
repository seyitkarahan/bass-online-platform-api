package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.dto.request.RatingCreateRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.RatingResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RatingRepository {

    private final JdbcTemplate jdbcTemplate;

    public RatingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Long studentId, RatingCreateRequest request) {

        String sql = """
            INSERT INTO ratings (student_id, course_id, rating, comment, created_at)
            SELECT ?, ?, ?, ?, NOW()
            WHERE EXISTS (
                SELECT 1 FROM enrollments
                WHERE student_id = ?
                  AND course_id = ?
                  AND active = true
            )
        """;

        int updated = jdbcTemplate.update(
                sql,
                studentId,
                request.getCourseId(),
                request.getRating(),
                request.getComment(),
                studentId,
                request.getCourseId()
        );

        if (updated == 0) {
            throw new RuntimeException("Student not enrolled in this course");
        }
    }

    public List<RatingResponse> findByCourse(Long courseId) {

        String sql = """
            SELECT student_id, rating, comment, created_at
            FROM ratings
            WHERE course_id = ?
            ORDER BY created_at DESC
        """;

        return jdbcTemplate.query(
                sql,
                (rs, i) -> RatingResponse.builder()
                        .studentId(rs.getLong("student_id"))
                        .rating(rs.getInt("rating"))
                        .comment(rs.getString("comment"))
                        .build(),
                courseId
        );
    }
}
