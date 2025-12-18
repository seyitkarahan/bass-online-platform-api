package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.entity.Instructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class InstructorRepository {

    private final JdbcTemplate jdbcTemplate;

    public InstructorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Instructor> findByUserId(Long userId) {
        String sql = "SELECT * FROM instructors WHERE user_id = ?";
        return jdbcTemplate.query(sql, (rs, i) ->
                Instructor.builder()
                        .id(rs.getLong("id"))
                        .userId(rs.getLong("user_id"))
                        .bio(rs.getString("bio"))
                        .expertise(rs.getString("expertise"))
                        .build(), userId
        ).stream().findFirst();
    }

    public Long save(Instructor instructor) {
        String sql = """
            INSERT INTO instructors (user_id, bio, expertise)
            VALUES (?, ?, ?)
            RETURNING id
        """;

        return jdbcTemplate.queryForObject(
                sql,
                Long.class,
                instructor.getUserId(),
                instructor.getBio(),
                instructor.getExpertise()
        );
    }
}
