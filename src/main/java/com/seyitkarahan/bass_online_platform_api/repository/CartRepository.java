package com.seyitkarahan.bass_online_platform_api.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepository {

    private final JdbcTemplate jdbcTemplate;

    public CartRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long createCart(Long studentId) {
        String sql = """
            INSERT INTO shopping_cart (student_id)
            VALUES (?)
            RETURNING id
        """;

        return jdbcTemplate.queryForObject(sql, Long.class, studentId);
    }

    public Long findCartIdByStudent(Long studentId) {
        String sql = """
            SELECT id
            FROM shopping_cart
            WHERE student_id = ?
        """;

        return jdbcTemplate.query(
                sql,
                (rs, i) -> rs.getLong("id"),
                studentId
        ).stream().findFirst().orElse(null);
    }
}

