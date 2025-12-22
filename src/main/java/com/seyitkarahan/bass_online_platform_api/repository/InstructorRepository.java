package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.entity.Instructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InstructorRepository {

    private final JdbcTemplate jdbcTemplate;

    public InstructorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Long> findInstructorIdByUserEmail(String email) {

        String sql = """
            SELECT i.id
            FROM instructors i
            JOIN users u ON i.user_id = u.id
            WHERE u.email = ?
        """;

        List<Long> result = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> rs.getLong("id"),
                email
        );

        return result.stream().findFirst();
    }
}
