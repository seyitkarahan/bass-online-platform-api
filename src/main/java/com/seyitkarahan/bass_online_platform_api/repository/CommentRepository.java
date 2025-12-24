package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.dto.request.CommentCreateRequest;
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

    public void save(Long userId, Long studentId, CommentCreateRequest request) {

        String sql = """
            INSERT INTO comments (user_id, course_id, content, created_at)
            SELECT ?, ?, ?, NOW()
            WHERE EXISTS (
                SELECT 1 FROM enrollments
                WHERE student_id = ?
                  AND course_id = ?
                  AND active = true
            )
        """;

        int updated = jdbcTemplate.update(
                sql,
                userId,
                request.getCourseId(),
                request.getContent(),
                studentId,
                request.getCourseId()
        );

        if (updated == 0) {
            throw new RuntimeException("User not enrolled in this course");
        }
    }

    public List<CommentResponse> findByCourse(Long courseId) {

        String sql = """
            SELECT u.name, c.content, c.created_at
            FROM comments c
            JOIN users u ON u.id = c.user_id
            WHERE c.course_id = ?
            ORDER BY c.created_at DESC
        """;

        return jdbcTemplate.query(
                sql,
                (rs, i) -> CommentResponse.builder()
                        .userName(rs.getString("name"))
                        .content(rs.getString("content"))
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .build(),
                courseId
        );
    }

    public List<CommentResponse> findAll() {
        String sql = """
        SELECT c.id, c.content, c.created_at, u.email
        FROM comments c
        JOIN users u ON u.id = c.user_id
        ORDER BY c.created_at DESC
    """;

        return jdbcTemplate.query(sql,
                (rs, i) -> new CommentResponse(
                        rs.getString("userName"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
    }

    public void delete(Long commentId) {
        jdbcTemplate.update("DELETE FROM comments WHERE id = ?", commentId);
    }

}
