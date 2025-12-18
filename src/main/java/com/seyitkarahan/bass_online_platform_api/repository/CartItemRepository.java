package com.seyitkarahan.bass_online_platform_api.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CartItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public CartItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addCourse(Long cartId, Long courseId) {
        String sql = """
            INSERT INTO shopping_cart_items (cart_id, course_id)
            VALUES (?, ?)
        """;
        jdbcTemplate.update(sql, cartId, courseId);
    }

    public void removeCourse(Long cartId, Long courseId) {
        jdbcTemplate.update(
                "DELETE FROM shopping_cart_items WHERE cart_id = ? AND course_id = ?",
                cartId, courseId
        );
    }
}
