package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.dto.response.CartItemResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public CartItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addItem(Long cartId, Long courseId) {
        String sql = """
            INSERT INTO shopping_cart_items (cart_id, course_id)
            VALUES (?, ?)
        """;

        jdbcTemplate.update(sql, cartId, courseId);
    }

    public boolean exists(Long cartId, Long courseId) {
        String sql = """
            SELECT COUNT(*)
            FROM shopping_cart_items
            WHERE cart_id = ? AND course_id = ?
        """;

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                cartId,
                courseId
        );

        return count != null && count > 0;
    }

    public void removeItem(Long cartItemId) {
        jdbcTemplate.update(
                "DELETE FROM shopping_cart_items WHERE id = ?",
                cartItemId
        );
    }

    public void clearCart(Long cartId) {
        String sql = """
            DELETE FROM shopping_cart_items
            WHERE cart_id = ?
        """;

        jdbcTemplate.update(sql, cartId);
    }

    public List<CartItemResponse> findItems(Long cartId) {
        String sql = """
            SELECT
                sci.id   AS cart_item_id,
                c.id     AS course_id,
                c.title,
                c.price
            FROM shopping_cart_items sci
            JOIN courses c ON c.id = sci.course_id
            WHERE sci.cart_id = ?
        """;

        return jdbcTemplate.query(sql,
                (rs, i) -> new CartItemResponse(
                        rs.getLong("cart_item_id"),
                        rs.getLong("course_id"),
                        rs.getString("title"),
                        rs.getDouble("price")
                ),
                cartId
        );
    }
}
