package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.entity.Course;
import com.seyitkarahan.bass_online_platform_api.repository.rowmapper.CourseRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CourseRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CourseRowMapper rowMapper;

    public CourseRepository(JdbcTemplate jdbcTemplate, CourseRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    public List<Course> findAll() {
        return jdbcTemplate.query("SELECT * FROM courses", rowMapper);
    }

    public Optional<Course> findById(Long id) {
        String sql = "SELECT * FROM courses WHERE id = ?";
        return jdbcTemplate.query(sql, rowMapper, id)
                .stream().findFirst();
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
}
