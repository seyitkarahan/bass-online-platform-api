package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.dto.response.CommentResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepository {

    private final JdbcTemplate jdbcTemplate;

    public CommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Long userId, Long courseId, String content) {
        String sql = """
            INSERT INTO comments (user_id, course_id, content)
            VALUES (?, ?, ?)
        """;

        jdbcTemplate.update(sql, userId, courseId, content);
    }

    public List<CommentResponse> findByCourse(Long courseId) {
        String sql = """
            SELECT c.content, c.created_at, u.name
            FROM comments c
            JOIN users u ON u.id = c.user_id
            WHERE c.course_id = ?
            ORDER BY c.created_at DESC
        """;

        return jdbcTemplate.query(
                sql,
                (rs, i) -> new CommentResponse(
                        rs.getString("content"),
                        rs.getString("name"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ),
                courseId
        );
    }
}
