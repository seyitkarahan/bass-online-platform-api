package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.entity.Cart;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CartRepository {

    private final JdbcTemplate jdbcTemplate;

    public CartRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long create(Long studentId) {
        String sql = """
            INSERT INTO shopping_cart (student_id)
            VALUES (?)
            RETURNING id
        """;

        return jdbcTemplate.queryForObject(sql, Long.class, studentId);
    }

    public Optional<Cart> findByStudentId(Long studentId) {
        String sql = "SELECT * FROM shopping_cart WHERE student_id = ?";
        return jdbcTemplate.query(sql, (rs, i) ->
                Cart.builder()
                        .id(rs.getLong("id"))
                        .studentId(rs.getLong("student_id"))
                        .build(), studentId
        ).stream().findFirst();
    }
}
