package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.entity.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class StudentRepository {

    private final JdbcTemplate jdbcTemplate;

    public StudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Student student) {
        String sql = """
            INSERT INTO students (user_id, grade_level)
            VALUES (?, ?)
            RETURNING id
        """;

        return jdbcTemplate.queryForObject(
                sql,
                Long.class,
                student.getUserId(),
                student.getGradeLevel()
        );
    }

    public Optional<Student> findByUserId(Long userId) {
        String sql = "SELECT * FROM students WHERE user_id = ?";
        return jdbcTemplate.query(sql, (rs, i) ->
                Student.builder()
                        .id(rs.getLong("id"))
                        .userId(rs.getLong("user_id"))
                        .gradeLevel(rs.getString("grade_level"))
                        .build(), userId
        ).stream().findFirst();
    }
}
