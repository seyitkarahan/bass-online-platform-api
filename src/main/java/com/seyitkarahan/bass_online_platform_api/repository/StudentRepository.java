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

    public Optional<Long> findStudentIdByUserEmail(String email) {
        String sql = """
            SELECT s.id
            FROM students s
            JOIN users u ON u.id = s.user_id
            WHERE u.email = ?
        """;

        return jdbcTemplate.query(sql,
                rs -> rs.next()
                        ? Optional.of(rs.getLong("id"))
                        : Optional.empty(),
                email
        );
    }
}
