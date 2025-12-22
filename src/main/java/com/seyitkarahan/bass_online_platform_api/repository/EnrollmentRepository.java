package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.entity.Enrollment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EnrollmentRepository {

    private final JdbcTemplate jdbcTemplate;

    public EnrollmentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean exists(Long studentId, Long courseId) {
        String sql = """
            SELECT COUNT(*) FROM enrollments
            WHERE student_id = ? AND course_id = ?
        """;

        Integer count = jdbcTemplate.queryForObject(
                sql, Integer.class, studentId, courseId
        );

        return count != null && count > 0;
    }

    public Long save(Long studentId, Long courseId) {
        String sql = """
            INSERT INTO enrollments (student_id, course_id)
            VALUES (?, ?)
            RETURNING id
        """;

        return jdbcTemplate.queryForObject(
                sql, Long.class, studentId, courseId
        );
    }
}
