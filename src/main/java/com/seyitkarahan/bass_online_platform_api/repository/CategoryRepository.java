package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.dto.response.CategoryResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(String name, String description) {
        String sql = """
            INSERT INTO categories (name, description)
            VALUES (?, ?)
            RETURNING id
        """;

        return jdbcTemplate.queryForObject(
                sql,
                Long.class,
                name,
                description
        );
    }

    public List<CategoryResponse> findAll() {
        String sql = """
            SELECT id, name, description
            FROM categories
            ORDER BY name
        """;

        return jdbcTemplate.query(sql,
                (rs, i) -> new CategoryResponse(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description")
                ));
    }

    public boolean existsByName(String name) {
        String sql = "SELECT COUNT(*) FROM categories WHERE name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, name);
        return count != null && count > 0;
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM categories WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void update(Long id, String name, String description) {
        String sql = """
            UPDATE categories
            SET name = ?, description = ?
            WHERE id = ?
        """;

        int updated = jdbcTemplate.update(sql, name, description, id);

        if (updated == 0) {
            throw new RuntimeException("Category not found");
        }
    }

    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM categories WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    public boolean existsByNameAndNotId(String name, Long id) {
        String sql = """
            SELECT COUNT(*)
            FROM categories
            WHERE name = ? AND id <> ?
        """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, name, id);
        return count != null && count > 0;
    }
}
