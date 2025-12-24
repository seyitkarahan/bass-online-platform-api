package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.dto.response.CourseResponse;
import com.seyitkarahan.bass_online_platform_api.entity.Course;
import com.seyitkarahan.bass_online_platform_api.repository.rowmapper.CourseRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class CourseRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CourseRowMapper rowMapper;

    public CourseRepository(JdbcTemplate jdbcTemplate, CourseRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    public Long save(Course course) {
        String sql = """
            INSERT INTO courses (instructor_id, category_id, title, description, price)
            VALUES (?, ?, ?, ?, ?)
            RETURNING id
        """;

        return jdbcTemplate.queryForObject(
                sql,
                Long.class,
                course.getInstructorId(),
                course.getCategoryId(),
                course.getTitle(),
                course.getDescription(),
                course.getPrice()
        );
    }

    public Course findById(Long id) {
        String sql = "SELECT * FROM courses WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public void update(Course course) {
        String sql = """
            UPDATE courses
            SET title = ?, description = ?, price = ?, category_id = ?
            WHERE id = ?
        """;

        jdbcTemplate.update(
                sql,
                course.getTitle(),
                course.getDescription(),
                course.getPrice(),
                course.getCategoryId(),
                course.getId()
        );
    }

    public List<CourseResponse> findAll() {
        String sql = """
        SELECT id, title, price, created_at
        FROM courses
    """;

        return jdbcTemplate.query(sql,
                (rs, i) -> new CourseResponse(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getBigDecimal("price")
                ));
    }

    public void delete(Long courseId) {
        jdbcTemplate.update("DELETE FROM courses WHERE id = ?", courseId);
    }

    public boolean isOwner(Long courseId, Long instructorId) {
        String sql = """
            SELECT COUNT(*) FROM courses
            WHERE id = ? AND instructor_id = ?
        """;

        Integer count = jdbcTemplate.queryForObject(
                sql, Integer.class, courseId, instructorId
        );

        return count != null && count > 0;
    }

    public BigDecimal getPrice(Long courseId) {
        String sql = "SELECT price FROM courses WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, courseId);
    }
}
