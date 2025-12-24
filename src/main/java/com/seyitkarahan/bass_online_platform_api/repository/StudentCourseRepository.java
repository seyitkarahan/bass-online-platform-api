package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.dto.response.StudentCourseResponse;
import com.seyitkarahan.bass_online_platform_api.repository.rowmapper.StudentCourseRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentCourseRepository {

    private final JdbcTemplate jdbcTemplate;
    private final StudentCourseRowMapper rowMapper;

    public StudentCourseRepository(
            JdbcTemplate jdbcTemplate,
            StudentCourseRowMapper rowMapper
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    public List<StudentCourseResponse> findMyCourses(Long studentId) {

        String sql = """
            SELECT
                c.id AS course_id,
                c.title,
                c.description,
                c.price,
                cat.name AS category_name,
                e.enrollment_date
            FROM enrollments e
            JOIN courses c ON c.id = e.course_id
            JOIN categories cat ON cat.id = c.category_id
            WHERE e.student_id = ?
              AND e.active = true
            ORDER BY e.enrollment_date DESC
        """;

        return jdbcTemplate.query(sql, rowMapper, studentId);
    }
}
