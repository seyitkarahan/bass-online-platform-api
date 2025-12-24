package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.dto.response.InstructorStudentResponse;
import com.seyitkarahan.bass_online_platform_api.dto.response.MyCourseResponse;
import com.seyitkarahan.bass_online_platform_api.entity.Enrollment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<MyCourseResponse> findMyCourses(Long studentId) {
        String sql = """
            SELECT 
                c.id AS course_id,
                c.title,
                c.description,
                e.enrollment_date
            FROM enrollments e
            JOIN courses c ON c.id = e.course_id
            WHERE e.student_id = ?
              AND e.active = true
            ORDER BY e.enrollment_date DESC
        """;

        return jdbcTemplate.query(
                sql,
                (rs, i) -> MyCourseResponse.builder()
                        .courseId(rs.getLong("course_id"))
                        .title(rs.getString("title"))
                        .description(rs.getString("description"))
                        .enrolledAt(
                                rs.getTimestamp("enrollment_date").toLocalDateTime()
                        )
                        .build(),
                studentId
        );
    }

    public List<InstructorStudentResponse> findStudentsByCourse(
            Long instructorId,
            Long courseId
    ) {
        String sql = """
            SELECT 
                s.id AS student_id,
                u.name,
                u.email,
                e.enrollment_date
            FROM enrollments e
            JOIN students s ON s.id = e.student_id
            JOIN users u ON u.id = s.user_id
            JOIN courses c ON c.id = e.course_id
            WHERE c.instructor_id = ?
              AND c.id = ?
              AND e.active = true
            ORDER BY e.enrollment_date DESC
        """;

        return jdbcTemplate.query(
                sql,
                (rs, i) -> InstructorStudentResponse.builder()
                        .studentId(rs.getLong("student_id"))
                        .studentName(rs.getString("name"))
                        .studentEmail(rs.getString("email"))
                        .enrolledAt(
                                rs.getTimestamp("enrollment_date").toLocalDateTime()
                        )
                        .build(),
                instructorId,
                courseId
        );
    }
}
