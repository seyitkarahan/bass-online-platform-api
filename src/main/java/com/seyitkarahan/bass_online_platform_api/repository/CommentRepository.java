package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.entity.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepository {

    private final JdbcTemplate jdbcTemplate;

    public CommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Comment> findByCourseId(Long courseId) {
        String sql = "SELECT * FROM comments WHERE course_id = ?";
        return jdbcTemplate.query(sql, (rs, i) ->
                Comment.builder()
                        .id(rs.getLong("id"))
                        .userId(rs.getLong("user_id"))
                        .courseId(rs.getLong("course_id"))
                        .content(rs.getString("content"))
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .build(), courseId
        );
    }

    public Long save(Comment comment) {
        String sql = """
            INSERT INTO comments (user_id, course_id, content)
            VALUES (?, ?, ?)
            RETURNING id
        """;

        return jdbcTemplate.queryForObject(
                sql,
                Long.class,
                comment.getUserId(),
                comment.getCourseId(),
                comment.getContent()
        );
    }
}
